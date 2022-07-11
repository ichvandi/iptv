package com.vandoc.iptv.util

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import java.net.UnknownHostException

const val DNS_CLOUDFLARE_QUERY = "https://1.1.1.1/dns-query"
const val DNS_CLOUDFLARE_PRIMARY = "1.1.1.1"
const val DNS_CLOUDFLARE_SECONDARY = "1.0.0.1"
const val DNS_CLOUDFLARE_PRIMARY_IPV6 = "2606:4700:4700::1111"
const val DNS_CLOUDFLARE_SECONDARY_IPV6 = "2606:4700:4700::1001"


class DnsResolver(client: OkHttpClient) : Dns {
    private val doh by lazy {
        DnsOverHttps.Builder().client(client)
            .url(DNS_CLOUDFLARE_QUERY.toHttpUrl())
            .bootstrapDnsHosts(
                listOf(
                    InetAddress.getByName(DNS_CLOUDFLARE_PRIMARY),
                    InetAddress.getByName(DNS_CLOUDFLARE_SECONDARY),
                    InetAddress.getByName(DNS_CLOUDFLARE_PRIMARY_IPV6),
                    InetAddress.getByName(DNS_CLOUDFLARE_SECONDARY_IPV6),
                )
            )
            .build()
    }

    override fun lookup(hostname: String): List<InetAddress> {
        try {
            return doh.lookup(hostname)
        } catch (e: UnknownHostException) {
        }
        return Dns.SYSTEM.lookup(hostname)
    }
}