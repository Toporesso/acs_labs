<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Crypto Exchanges</title>
            </head>
            <body>
                <h1>Crypto Exchanges</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Country</th>
                        <th>Website</th>
                        <th>Coins</th>
                    </tr>
                    <xsl:for-each select="List/item">
                        <td>
                            <xsl:for-each select="cryptos">
                                <div><xsl:value-of select="name"/> (<xsl:value-of select="symbol"/>) - $<xsl:value-of select="priceUsd"/></div>
                            </xsl:for-each>
                        </td>

                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="country"/></td>
                            <td><xsl:value-of select="website"/></td>
                            <td>
                                <xsl:for-each select="cryptos">
                                    <div><xsl:value-of select="name"/> (<xsl:value-of select="symbol"/>)</div>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <a href="/api/coins-xsl">View Coins</a>
                <a href="/exchanges">Traditional View</a>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>