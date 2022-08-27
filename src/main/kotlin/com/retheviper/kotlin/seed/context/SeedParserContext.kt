package com.retheviper.kotlin.seed.context

class SeedParserContext(
    /**
     *  date formatting
     *  default value: **yyyy-MM-dd**
     */
    var dateFormat: String = "yyyy-MM-dd",

    /**
     * time formatting
     * default value: **HH:mm**
     */
    var timeFormat: String = "HH:mm",

    /**
     * date time separator
     * default value: **(space)**
     */
    var dateTimeSeparator: String = " ",

    /**
     * trim white spaces in csv column content
     */
    var trimWhiteSpace: Boolean = true
)