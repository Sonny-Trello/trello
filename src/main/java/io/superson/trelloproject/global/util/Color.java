package io.superson.trelloproject.global.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Color {
    RED("#FF0000"),
    BLUE("#0000FF"),
    GREEN("#00FF00"),
    YELLOW("#FFFF00"),
    BLACK("#000000"),
    WHITE("#FFFFFF"),
    ;

    private final String hex;

}
