/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2015 ForgeRock AS.
 *
 * Tests (only) copied from IPv6 address validation Regular Expressions by Rich Brown <richard.e.brown@dartware.com>
 */
package org.forgerock.openam.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @since 13.0.0.
 */
public class ValidateIPAddressTest {

    @DataProvider(name = "addresses")
    public Object[][] getAddresses() {
        return new Object[][] {
            { false, "" },
            { true,  "::1" },
            { true,  "::" },
            { true,  "0:0:0:0:0:0:0:1" },
            { true,  "0:0:0:0:0:0:0:0" },
            { true,  "2001:DB8:0:0:8:800:200C:417A" },
            { true,  "FF01:0:0:0:0:0:0:101" },
            { true,  "2001:DB8::8:800:200C:417A" },
            { true,  "FF01::101" },
            { false, "2001:DB8:0:0:8:800:200C:417A:221" },
            { false, "FF01::101::2" },
            { true,  "fe80::217:f2ff:fe07:ed62" },

            { true,  "2001:0000:1234:0000:0000:C1C0:ABCD:0876" },
            { true,  "3ffe:0b00:0000:0000:0001:0000:0000:000a" },
            { true,  "FF02:0000:0000:0000:0000:0000:0000:0001" },
            { true,  "0000:0000:0000:0000:0000:0000:0000:0001" },
            { true,  "0000:0000:0000:0000:0000:0000:0000:0000" },
            { false, "02001:0000:1234:0000:0000:C1C0:ABCD:0876" },
            { false, "2001:0000:1234:0000:00001:C1C0:ABCD:0876" },
            { false, "2001:0000:1234:0000:0000:C1C0:ABCD:0876  0" },
            { false, "2001:0000:1234: 0000:0000:C1C0:ABCD:0876" },

            { false, "3ffe:0b00:0000:0001:0000:0000:000a" },
            { false, "FF02:0000:0000:0000:0000:0000:0000:0000:0001" },
            { false, "3ffe:b00::1::a" },
            { false, "::1111:2222:3333:4444:5555:6666::" },
            { true,  "2::10" },
            { true,  "ff02::1" },
            { true,  "fe80::" },
            { true,  "2002::" },
            { true,  "2001:db8::" },
            { true,  "2001:0db8:1234::" },
            { true,  "::ffff:0:0" },
            { true,  "::1" },
            { true,  "1:2:3:4:5:6:7:8" },
            { true,  "1:2:3:4:5:6::8" },
            { true,  "1:2:3:4:5::8" },
            { true,  "1:2:3:4::8" },
            { true,  "1:2:3::8" },
            { true,  "1:2::8" },
            { true,  "1::8" },
            { true,  "1::2:3:4:5:6:7" },
            { true,  "1::2:3:4:5:6" },
            { true,  "1::2:3:4:5" },
            { true,  "1::2:3:4" },
            { true,  "1::2:3" },
            { true,  "1::8" },
            { true,  "::2:3:4:5:6:7:8" },
            { true,  "::2:3:4:5:6:7" },
            { true,  "::2:3:4:5:6" },
            { true,  "::2:3:4:5" },
            { true,  "::2:3:4" },
            { true,  "::2:3" },
            { true,  "::8" },
            { true,  "1:2:3:4:5:6::" },
            { true,  "1:2:3:4:5::" },
            { true,  "1:2:3:4::" },
            { true,  "1:2:3::" },
            { true,  "1:2::" },
            { true,  "1::" },
            { true,  "1:2:3:4:5::7:8" },
            { false, "1:2:3::4:5::7:8" },
            { false, "12345::6:7:8" },
            { true,  "1:2:3:4::7:8" },
            { true,  "1:2:3::7:8" },
            { true,  "1:2::7:8" },
            { true,  "1::7:8" },

            { true,  "1:2:3:4:5:6:1.2.3.4" },
            { false, "1:2:3:4:5:6:991.2.3.4" },
            { false, "1:2:3:4:5:6:256.2.3.4" },
            { true,  "1:2:3:4:5:6:255.255.255.255" },
            { false, "0:2:3:4:5:6:255.255.275.255" },
            { true,  "1:2:3:4:5::1.2.3.4" },
            { false, "1:2:3:4:5::1.882.3.4" },
            { true,  "1:2:3:4::1.2.3.4" },
            { false, "1:2:3:4::1.912.3.4" },
            { true,  "1:2:3::1.2.3.4" },
            { false, "1:2:3::1.2.823.4" },
            { true,  "1:2::1.2.3.4" },
            { false, "1:2::1.312.3.4" },
            { true,  "1::1.2.3.4" },
            { false, "1::411.2.3.4" },
            { true,  "1:2:3:4::5:1.2.3.4" },
            { false, "1:2:3:4::5:791.2.3.4" },
            { true,  "1:2:3::5:1.2.3.4" },
            { false, "1:2:3::5:681.2.3.4" },
            { false, "1:2:3::5:1.2.3.1004" },
            { true,  "1:2::5:1.2.3.4" },
            { false, "1:2::5:1.312.3.4" },
            { true,  "1::5:1.2.3.4" },
            { false, "1::5:1.552.3.4" },
            { true,  "1::5:11.22.33.44" },
            { false, "1::5:11.322.33.44" },
            { false, "1::5:400.2.3.4" },
            { false, "1::5:260.2.3.4" },
            { false, "1::5:256.2.3.4" },
            { false, "1::5:1.256.3.4" },
            { false, "1::5:1.2.256.4" },
            { false, "1::5:1.2.3.256" },
            { false, "1::5:300.2.3.4" },
            { false, "1::5:1.300.3.4" },
            { false, "1::5:1.2.300.4" },
            { false, "1::5:1.2.3.300" },
            { false, "1::5:900.2.3.4" },
            { false, "1::5:1.900.3.4" },
            { false, "1::5:1.2.900.4" },
            { false, "1::5:1.2.3.900" },
            { false, "1::5:300.300.300.300" },
            { false, "1::5:3000.30.30.30" },
            { false, "1::400.2.3.4" },
            { false, "1::260.2.3.4" },
            { false, "1::256.2.3.4" },
            { false, "1::1.256.3.4" },
            { false, "1::1.2.256.4" },
            { false, "1::1.2.3.256" },
            { false, "1::300.2.3.4" },
            { false, "1::1.300.3.4" },
            { false, "1::1.2.300.4" },
            { false, "1::1.2.3.300" },
            { false, "1::900.2.3.4" },
            { false, "1::1.900.3.4" },
            { false, "1::1.2.900.4" },
            { false, "1::1.2.3.900" },
            { false, "1::300.300.300.300" },
            { false, "1::3000.30.30.30" },
            { false, "::400.2.3.4" },
            { false, "::260.2.3.4" },
            { false, "::256.2.3.4" },
            { false, "::1.256.3.4" },
            { false, "::1.2.256.4" },
            { false, "::1.2.3.256" },
            { false, "::300.2.3.4" },
            { false, "::1.300.3.4" },
            { false, "::1.2.300.4" },
            { false, "::1.2.3.300" },
            { false, "::900.2.3.4" },
            { false, "::1.900.3.4" },
            { false, "::1.2.900.4" },
            { false, "::1.2.3.900" },
            { false, "::300.300.300.300" },
            { false, "::3000.30.30.30" },
            { true,  "fe80::217:f2ff:254.7.237.98" },
            { true,  "::ffff:192.168.1.26" },
            { false, "2001:1:1:1:1:1:255Z255X255Y255" },
            { false, "::ffff:192x168.1.26" },
            { true,  "::ffff:192.168.1.1" },
            { true,  "0:0:0:0:0:0:13.1.68.3" },
            { true,  "0:0:0:0:0:FFFF:129.144.52.38" },
            { true,  "::13.1.68.3" },
            { true,  "::FFFF:129.144.52.38" },
            { true,  "fe80:0:0:0:204:61ff:254.157.241.86" },
            { true,  "fe80::204:61ff:254.157.241.86" },
            { true,  "::ffff:12.34.56.78" },
            { false, "::ffff:2.3.4" },
            { false, "::ffff:257.1.2.3" },
            { false, "1.2.3.4" },

            { false, "1.2.3.4:1111:2222:3333:4444::5555" },
            { false, "1.2.3.4:1111:2222:3333::5555" },
            { false, "1.2.3.4:1111:2222::5555" },
            { false, "1.2.3.4:1111::5555" },
            { false, "1.2.3.4::5555" },
            { false, "1.2.3.4::" },

            { false, "fe80:0000:0000:0000:0204:61ff:254.157.241.086" },
            { true,  "::ffff:192.0.2.128" },
            { false, "XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:6666:00.00.00.00" },
            { false, "1111:2222:3333:4444:5555:6666:000.000.000.000" },
            { false, "1111:2222:3333:4444:5555:6666:256.256.256.256" },

            { true,  "fe80:0000:0000:0000:0204:61ff:fe9d:f156" },
            { true,  "fe80:0:0:0:204:61ff:fe9d:f156" },
            { true,  "fe80::204:61ff:fe9d:f156" },
            { true,  "::1" },
            { true,  "fe80::" },
            { true,  "fe80::1" },
            { false, ":" },
            { true,  "::ffff:c000:280" },

            { false, "1111:2222:3333:4444::5555:" },
            { false, "1111:2222:3333::5555:" },
            { false, "1111:2222::5555:" },
            { false, "1111::5555:" },
            { false, "::5555:" },
            { false, ":::" },
            { false, "1111:" },
            { false, ":" },

            { false, ":1111:2222:3333:4444::5555" },
            { false, ":1111:2222:3333::5555" },
            { false, ":1111:2222::5555" },
            { false, ":1111::5555" },
            { false, ":::5555" },
            { false, ":::" },

            { true,  "2001:0db8:85a3:0000:0000:8a2e:0370:7334" },
            { true,  "2001:db8:85a3:0:0:8a2e:370:7334" },
            { true,  "2001:db8:85a3::8a2e:370:7334" },
            { true,  "2001:0db8:0000:0000:0000:0000:1428:57ab" },
            { true,  "2001:0db8:0000:0000:0000::1428:57ab" },
            { true,  "2001:0db8:0:0:0:0:1428:57ab" },
            { true,  "2001:0db8:0:0::1428:57ab" },
            { true,  "2001:0db8::1428:57ab" },
            { true,  "2001:db8::1428:57ab" },
            { true,  "0000:0000:0000:0000:0000:0000:0000:0001" },
            { true,  "::1" },
            { true,  "::ffff:0c22:384e" },
            { true,  "2001:0db8:1234:0000:0000:0000:0000:0000" },
            { true,  "2001:0db8:1234:ffff:ffff:ffff:ffff:ffff" },
            { true,  "2001:db8:a::123" },
            { true,  "fe80::" },

            { false, "123" },
            { false, "ldkfj" },
            { false, "2001::FFD3::57ab" },
            { false, "2001:db8:85a3::8a2e:37023:7334" },
            { false, "2001:db8:85a3::8a2e:370k:7334" },
            { false, "1:2:3:4:5:6:7:8:9" },
            { false, "1::2::3" },
            { false, "1:::3:4:5" },
            { false, "1:2:3::4:5:6:7:8:9" },

            { true,  "1111:2222:3333:4444:5555:6666:7777:8888" },
            { true,  "1111:2222:3333:4444:5555:6666:7777::" },
            { true,  "1111:2222:3333:4444:5555:6666::" },
            { true,  "1111:2222:3333:4444:5555::" },
            { true,  "1111:2222:3333:4444::" },
            { true,  "1111:2222:3333::" },
            { true,  "1111:2222::" },
            { true,  "1111::" },

            { true,  "1111:2222:3333:4444:5555:6666::8888" },
            { true,  "1111:2222:3333:4444:5555::8888" },
            { true,  "1111:2222:3333:4444::8888" },
            { true,  "1111:2222:3333::8888" },
            { true,  "1111:2222::8888" },
            { true,  "1111::8888" },
            { true,  "::8888" },
            { true,  "1111:2222:3333:4444:5555::7777:8888" },
            { true,  "1111:2222:3333:4444::7777:8888" },
            { true,  "1111:2222:3333::7777:8888" },
            { true,  "1111:2222::7777:8888" },
            { true,  "1111::7777:8888" },
            { true,  "::7777:8888" },
            { true,  "1111:2222:3333:4444::6666:7777:8888" },
            { true,  "1111:2222:3333::6666:7777:8888" },
            { true,  "1111:2222::6666:7777:8888" },
            { true,  "1111::6666:7777:8888" },
            { true,  "::6666:7777:8888" },
            { true,  "1111:2222:3333::5555:6666:7777:8888" },
            { true,  "1111:2222::5555:6666:7777:8888" },
            { true,  "1111::5555:6666:7777:8888" },
            { true,  "::5555:6666:7777:8888" },
            { true,  "1111:2222::4444:5555:6666:7777:8888" },
            { true,  "1111::4444:5555:6666:7777:8888" },
            { true,  "::4444:5555:6666:7777:8888" },
            { true,  "1111::3333:4444:5555:6666:7777:8888" },
            { true,  "::3333:4444:5555:6666:7777:8888" },
            { true,  "::2222:3333:4444:5555:6666:7777:8888" },
            { true,  "1111:2222:3333:4444:5555:6666:123.123.123.123" },
            { true,  "1111:2222:3333:4444:5555::123.123.123.123" },
            { true,  "1111:2222:3333:4444::123.123.123.123" },
            { true,  "1111:2222:3333::123.123.123.123" },
            { true,  "1111:2222::123.123.123.123" },
            { true,  "1111::123.123.123.123" },
            { true,  "::123.123.123.123" },
            { true,  "1111:2222:3333:4444::6666:123.123.123.123" },
            { true,  "1111:2222:3333::6666:123.123.123.123" },
            { true,  "1111:2222::6666:123.123.123.123" },
            { true,  "1111::6666:123.123.123.123" },
            { true,  "::6666:123.123.123.123" },
            { true,  "1111:2222:3333::5555:6666:123.123.123.123" },
            { true,  "1111:2222::5555:6666:123.123.123.123" },
            { true,  "1111::5555:6666:123.123.123.123" },
            { true,  "::5555:6666:123.123.123.123" },
            { true,  "1111:2222::4444:5555:6666:123.123.123.123" },
            { true,  "1111::4444:5555:6666:123.123.123.123" },
            { true,  "::4444:5555:6666:123.123.123.123" },
            { true,  "1111::3333:4444:5555:6666:123.123.123.123" },
            { true,  "::2222:3333:4444:5555:6666:123.123.123.123" },

            { true,  "::0:0:0:0:0:0:0" },
            { true,  "::0:0:0:0:0:0" },
            { true,  "::0:0:0:0:0" },
            { true,  "::0:0:0:0" },
            { true,  "::0:0:0" },
            { true,  "::0:0" },
            { true,  "::0" },
            { true,  "0:0:0:0:0:0:0::" },
            { true,  "0:0:0:0:0:0::" },
            { true,  "0:0:0:0:0::" },
            { true,  "0:0:0:0::" },
            { true,  "0:0:0::" },
            { true,  "0:0::" },
            { true,  "0::" },

            { false, "XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX" },

            { false, "1111:2222:3333:4444:5555:6666:7777:8888:9999" },
            { false, "1111:2222:3333:4444:5555:6666:7777:8888::" },
            { false, "::2222:3333:4444:5555:6666:7777:8888:9999" },

            { false, "1111:2222:3333:4444:5555:6666:7777" },
            { false, "1111:2222:3333:4444:5555:6666" },
            { false, "1111:2222:3333:4444:5555" },
            { false, "1111:2222:3333:4444" },
            { false, "1111:2222:3333" },
            { false, "1111:2222" },
            { false, "1111" },

            { false, "11112222:3333:4444:5555:6666:7777:8888" },
            { false, "1111:22223333:4444:5555:6666:7777:8888" },
            { false, "1111:2222:33334444:5555:6666:7777:8888" },
            { false, "1111:2222:3333:44445555:6666:7777:8888" },
            { false, "1111:2222:3333:4444:55556666:7777:8888" },
            { false, "1111:2222:3333:4444:5555:66667777:8888" },
            { false, "1111:2222:3333:4444:5555:6666:77778888" },

            { false, "1111:2222:3333:4444:5555:6666:7777:8888:" },
            { false, "1111:2222:3333:4444:5555:6666:7777:" },
            { false, "1111:2222:3333:4444:5555:6666:" },
            { false, "1111:2222:3333:4444:5555:" },
            { false, "1111:2222:3333:4444:" },
            { false, "1111:2222:3333:" },
            { false, "1111:2222:" },
            { false, "1111:" },
            { false, ":" },
            { false, ":8888" },
            { false, ":7777:8888" },
            { false, ":6666:7777:8888" },
            { false, ":5555:6666:7777:8888" },
            { false, ":4444:5555:6666:7777:8888" },
            { false, ":3333:4444:5555:6666:7777:8888" },
            { false, ":2222:3333:4444:5555:6666:7777:8888" },
            { false, ":1111:2222:3333:4444:5555:6666:7777:8888" },

            { false, ":::2222:3333:4444:5555:6666:7777:8888" },
            { false, "1111:::3333:4444:5555:6666:7777:8888" },
            { false, "1111:2222:::4444:5555:6666:7777:8888" },
            { false, "1111:2222:3333:::5555:6666:7777:8888" },
            { false, "1111:2222:3333:4444:::6666:7777:8888" },
            { false, "1111:2222:3333:4444:5555:::7777:8888" },
            { false, "1111:2222:3333:4444:5555:6666:::8888" },
            { false, "1111:2222:3333:4444:5555:6666:7777:::" },

            { false, "::2222::4444:5555:6666:7777:8888" },
            { false, "::2222:3333::5555:6666:7777:8888" },
            { false, "::2222:3333:4444::6666:7777:8888" },
            { false, "::2222:3333:4444:5555::7777:8888" },
            { false, "::2222:3333:4444:5555:7777::8888" },
            { false, "::2222:3333:4444:5555:7777:8888::" },

            { false, "1111::3333::5555:6666:7777:8888" },
            { false, "1111::3333:4444::6666:7777:8888" },
            { false, "1111::3333:4444:5555::7777:8888" },
            { false, "1111::3333:4444:5555:6666::8888" },
            { false, "1111::3333:4444:5555:6666:7777::" },

            { false, "1111:2222::4444::6666:7777:8888" },
            { false, "1111:2222::4444:5555::7777:8888" },
            { false, "1111:2222::4444:5555:6666::8888" },
            { false, "1111:2222::4444:5555:6666:7777::" },

            { false, "1111:2222:3333::5555::7777:8888" },
            { false, "1111:2222:3333::5555:6666::8888" },
            { false, "1111:2222:3333::5555:6666:7777::" },

            { false, "1111:2222:3333:4444::6666::8888" },
            { false, "1111:2222:3333:4444::6666:7777::" },

            { false, "1111:2222:3333:4444:5555::7777::" },

            { false, "1111:2222:3333:4444:5555:6666:7777:8888:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:6666:7777:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:6666::1.2.3.4" },
            { false, "::2222:3333:4444:5555:6666:7777:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:6666:1.2.3.4.5" },

            { false, "1111:2222:3333:4444:5555:1.2.3.4" },
            { false, "1111:2222:3333:4444:1.2.3.4" },
            { false, "1111:2222:3333:1.2.3.4" },
            { false, "1111:2222:1.2.3.4" },
            { false, "1111:1.2.3.4" },
            { false, "1.2.3.4" },

            { false, "11112222:3333:4444:5555:6666:1.2.3.4" },
            { false, "1111:22223333:4444:5555:6666:1.2.3.4" },
            { false, "1111:2222:33334444:5555:6666:1.2.3.4" },
            { false, "1111:2222:3333:44445555:6666:1.2.3.4" },
            { false, "1111:2222:3333:4444:55556666:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:66661.2.3.4" },

            { false, "1111:2222:3333:4444:5555:6666:255255.255.255" },
            { false, "1111:2222:3333:4444:5555:6666:255.255255.255" },
            { false, "1111:2222:3333:4444:5555:6666:255.255.255255" },

            { false, ":1.2.3.4" },
            { false, ":6666:1.2.3.4" },
            { false, ":5555:6666:1.2.3.4" },
            { false, ":4444:5555:6666:1.2.3.4" },
            { false, ":3333:4444:5555:6666:1.2.3.4" },
            { false, ":2222:3333:4444:5555:6666:1.2.3.4" },
            { false, ":1111:2222:3333:4444:5555:6666:1.2.3.4" },

            { false, ":::2222:3333:4444:5555:6666:1.2.3.4" },
            { false, "1111:::3333:4444:5555:6666:1.2.3.4" },
            { false, "1111:2222:::4444:5555:6666:1.2.3.4" },
            { false, "1111:2222:3333:::5555:6666:1.2.3.4" },
            { false, "1111:2222:3333:4444:::6666:1.2.3.4" },
            { false, "1111:2222:3333:4444:5555:::1.2.3.4" },

            { false, "::2222::4444:5555:6666:1.2.3.4" },
            { false, "::2222:3333::5555:6666:1.2.3.4" },
            { false, "::2222:3333:4444::6666:1.2.3.4" },
            { false, "::2222:3333:4444:5555::1.2.3.4" },

            { false, "1111::3333::5555:6666:1.2.3.4" },
            { false, "1111::3333:4444::6666:1.2.3.4" },
            { false, "1111::3333:4444:5555::1.2.3.4" },

            { false, "1111:2222::4444::6666:1.2.3.4" },
            { false, "1111:2222::4444:5555::1.2.3.4" },

            { false, "1111:2222:3333::5555::1.2.3.4" },

            { false, "::." },
            { false, "::.." },
            { false, "::..." },
            { false, "::1..." },
            { false, "::1.2.." },
            { false, "::1.2.3." },
            { false, "::.2.." },
            { false, "::.2.3." },
            { false, "::.2.3.4" },
            { false, "::..3." },
            { false, "::..3.4" },
            { false, "::...4" },

            { false, ":1111:2222:3333:4444:5555:6666:7777::" },
            { false, ":1111:2222:3333:4444:5555:6666::" },
            { false, ":1111:2222:3333:4444:5555::" },
            { false, ":1111:2222:3333:4444::" },
            { false, ":1111:2222:3333::" },
            { false, ":1111:2222::" },
            { false, ":1111::" },
            { false, ":::" },
            { false, ":1111:2222:3333:4444:5555:6666::8888" },
            { false, ":1111:2222:3333:4444:5555::8888" },
            { false, ":1111:2222:3333:4444::8888" },
            { false, ":1111:2222:3333::8888" },
            { false, ":1111:2222::8888" },
            { false, ":1111::8888" },
            { false, ":::8888" },
            { false, ":1111:2222:3333:4444:5555::7777:8888" },
            { false, ":1111:2222:3333:4444::7777:8888" },
            { false, ":1111:2222:3333::7777:8888" },
            { false, ":1111:2222::7777:8888" },
            { false, ":1111::7777:8888" },
            { false, ":::7777:8888" },
            { false, ":1111:2222:3333:4444::6666:7777:8888" },
            { false, ":1111:2222:3333::6666:7777:8888" },
            { false, ":1111:2222::6666:7777:8888" },
            { false, ":1111::6666:7777:8888" },
            { false, ":::6666:7777:8888" },
            { false, ":1111:2222:3333::5555:6666:7777:8888" },
            { false, ":1111:2222::5555:6666:7777:8888" },
            { false, ":1111::5555:6666:7777:8888" },
            { false, ":::5555:6666:7777:8888" },
            { false, ":1111:2222::4444:5555:6666:7777:8888" },
            { false, ":1111::4444:5555:6666:7777:8888" },
            { false, ":::4444:5555:6666:7777:8888" },
            { false, ":1111::3333:4444:5555:6666:7777:8888" },
            { false, ":::3333:4444:5555:6666:7777:8888" },
            { false, ":::2222:3333:4444:5555:6666:7777:8888" },
            { false, ":1111:2222:3333:4444:5555:6666:1.2.3.4" },
            { false, ":1111:2222:3333:4444:5555::1.2.3.4" },
            { false, ":1111:2222:3333:4444::1.2.3.4" },
            { false, ":1111:2222:3333::1.2.3.4" },
            { false, ":1111:2222::1.2.3.4" },
            { false, ":1111::1.2.3.4" },
            { false, ":::1.2.3.4" },
            { false, ":1111:2222:3333:4444::6666:1.2.3.4" },
            { false, ":1111:2222:3333::6666:1.2.3.4" },
            { false, ":1111:2222::6666:1.2.3.4" },
            { false, ":1111::6666:1.2.3.4" },
            { false, ":::6666:1.2.3.4" },
            { false, ":1111:2222:3333::5555:6666:1.2.3.4" },
            { false, ":1111:2222::5555:6666:1.2.3.4" },
            { false, ":1111::5555:6666:1.2.3.4" },
            { false, ":::5555:6666:1.2.3.4" },
            { false, ":1111:2222::4444:5555:6666:1.2.3.4" },
            { false, ":1111::4444:5555:6666:1.2.3.4" },
            { false, ":::4444:5555:6666:1.2.3.4" },
            { false, ":1111::3333:4444:5555:6666:1.2.3.4" },
            { false, ":::2222:3333:4444:5555:6666:1.2.3.4" },

            { false, "1111:2222:3333:4444:5555:6666:7777:::" },
            { false, "1111:2222:3333:4444:5555:6666:::" },
            { false, "1111:2222:3333:4444:5555:::" },
            { false, "1111:2222:3333:4444:::" },
            { false, "1111:2222:3333:::" },
            { false, "1111:2222:::" },
            { false, "1111:::" },
            { false, ":::" },
            { false, "1111:2222:3333:4444:5555:6666::8888:" },
            { false, "1111:2222:3333:4444:5555::8888:" },
            { false, "1111:2222:3333:4444::8888:" },
            { false, "1111:2222:3333::8888:" },
            { false, "1111:2222::8888:" },
            { false, "1111::8888:" },
            { false, "::8888:" },
            { false, "1111:2222:3333:4444:5555::7777:8888:" },
            { false, "1111:2222:3333:4444::7777:8888:" },
            { false, "1111:2222:3333::7777:8888:" },
            { false, "1111:2222::7777:8888:" },
            { false, "1111::7777:8888:" },
            { false, "::7777:8888:" },
            { false, "1111:2222:3333:4444::6666:7777:8888:" },
            { false, "1111:2222:3333::6666:7777:8888:" },
            { false, "1111:2222::6666:7777:8888:" },
            { false, "1111::6666:7777:8888:" },
            { false, "::6666:7777:8888:" },
            { false, "1111:2222:3333::5555:6666:7777:8888:" },
            { false, "1111:2222::5555:6666:7777:8888:" },
            { false, "1111::5555:6666:7777:8888:" },
            { false, "::5555:6666:7777:8888:" },
            { false, "1111:2222::4444:5555:6666:7777:8888:" },
            { false, "1111::4444:5555:6666:7777:8888:" },
            { false, "::4444:5555:6666:7777:8888:" },
            { false, "1111::3333:4444:5555:6666:7777:8888:" },
            { false, "::3333:4444:5555:6666:7777:8888:" },
            { false, "::2222:3333:4444:5555:6666:7777:8888:" },

            { true,  "0:a:b:c:d:e:f::" },
            { true,  "::0:a:b:c:d:e:f" },
            { true,  "a:b:c:d:e:f:0::" },
            { false, "':10.0.0.1" }
        };
    }

    @Test(dataProvider = "addresses")
    public void testWildcardInProtocol(boolean expected, String address) {

        boolean result = ValidateIPaddress.isIPv6(address);

        assertEquals(result, expected);
    }

}
