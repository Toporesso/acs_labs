<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Crypto Coins</title>
            </head>
            <body>
                <h1>Crypto Coins</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Symbol</th>
                        <th>Price</th>
                        <th>Market Cap</th>
                        <th>Exchanges</th>
                    </tr>
                    <xsl:for-each select="List/item">
                        <td>
                            <xsl:for-each select="exchanges/exchange">
                                <div><xsl:value-of select="name"/> (<xsl:value-of select="country"/>)</div>
                            </xsl:for-each>
                        </td>
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="symbol"/></td>
                            <td><xsl:value-of select="priceUsd"/></td>
                            <td><xsl:value-of select="marketCap"/></td>
                            <td>
                                <xsl:for-each select="exchanges/exchange">
                                    <div><xsl:value-of select="name"/></div>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <a href="/api/exchanges-xsl">View Exchanges</a>
                <a href="/coins">Traditional View</a>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>