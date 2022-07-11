package com.vandoc.iptv.util

class UnknownResourceException : Exception() {
    override val message: String
        get() = "Resource type not found!"
}