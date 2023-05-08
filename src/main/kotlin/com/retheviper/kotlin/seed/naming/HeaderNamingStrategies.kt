package com.retheviper.kotlin.seed.naming

enum class HeaderNamingStrategies {
    /**
     * headerName to header_name
     */
    CAMEL_TO_SNAKE,

    /**
     * headerName to HEADER_NAME
     */
    CAMEL_TO_UPPER_SNAKE,

    /**
     * headerName to header name
     */
    CAMEL_TO_SPACE,

    /**
     * headerName to HEADER NAME
     */
    CAMEL_TO_UPPER_SPACE,

    /**
     * headerName to header-name
     */
    CAMEL_TO_KEBAB,

    /**
     * headerName to HEADER-NAME
     */
    CAMEL_TO_UPPER_KEBAB
}