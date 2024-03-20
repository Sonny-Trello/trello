package io.superson.trelloproject.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.superson.trelloproject.domain.common.dto.ExceptionDto;
import io.superson.trelloproject.global.impl.UserDetailsServiceImpl;
import io.superson.trelloproject.global.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class AuthorizationFilter extends
    OncePerRequestFilter { // OncePerRequestFilter : 요청 한번마다 필터 돌리기

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain
    ) throws ServletException, IOException {

        //토큰 받기
        String token = jwtUtil.resolveToken(httpServletRequest);

        if (Objects.nonNull(token)) { // 만약 토큰이 있으면
            if (jwtUtil.validateToken(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token); //token 에서 info 추출
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetails userDetails = userDetailsServiceImpl.getUserDetails(
                    info);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                // @AuthenticationPrincipal 로 유저정보 조회 가능
            } else {
                // 인증정보가  존재하지 않을때
                ExceptionDto statusResponseDto = ExceptionDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("토큰이 유효하지 않습니다.")
                    .build();
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter()
                    .write(objectMapper.writeValueAsString(statusResponseDto));
                // write 타입은 스트링 이므로 ,objectMapper 를 통해 맵핑 해서 String 으로변환
                return;
                // 더 필터를 타지 않고 에러응답을 리턴시킴
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
