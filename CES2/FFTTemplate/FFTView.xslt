<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="http://my.opera.com/VonPower/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes">
    <xsl:output version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="no" media-type="text/html" />
    <xsl:template match="/">
        <html>
            <head>
                <title />
            </head>
            <body>
                <xsl:for-each select="n1:FFTable">
                    <a style="border-bottom-color:#000080; border-bottom-style:solid; border-bottom-width:medium; ">
                        <xsl:attribute name="name"><xsl:text disable-output-escaping="yes">top</xsl:text></xsl:attribute>
                    </a>
                    <div style="border-bottom-color:#000080; border-bottom-style:solid; border-bottom-width:medium; ">
                        <xsl:for-each select="@Name">
                            <xsl:value-of select="." />因素因子权重值赋值表</xsl:for-each>
                        <br />
                    </div>&#160;<table border="1" border-collapse="separate">
                        <thead>
                            <tr bgcolor="#FF8040">
                                <td width="10%">
                                    <span style="font-weight:bold; ">因素类别</span>
                                </td>
                                <td width="5%">
                                    <span style="font-weight:bold; ">权重</span>
                                </td>
                                <td width="85%" />
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="n1:FactorCatalog">
                                <tr>
                                    <td width="10%">
                                        <xsl:for-each select="@Name">
                                            <xsl:value-of select="." />
                                        </xsl:for-each>
                                    </td>
                                    <td width="5%">
                                        <xsl:for-each select="@Weight">
                                            <xsl:value-of select="." />
                                        </xsl:for-each>
                                    </td>
                                    <td width="85%">
                                        <table border="1" frame="void">
                                            <thead>
                                                <tr bgcolor="#FF8000" width="5%">
                                                    <td width="10%">
                                                        <span style="font-weight:bold; ">因素</span>
                                                    </td>
                                                    <td>
                                                        <span style="font-weight:bold; ">权重</span>
                                                    </td>
                                                    <td width="85%" />
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <xsl:for-each select="n1:BasicFactor">
                                                    <tr>
                                                        <td width="10%">
                                                            <xsl:for-each select="@Name">
                                                                <xsl:value-of select="." />
                                                            </xsl:for-each>
                                                        </td>
                                                        <td>
                                                            <xsl:for-each select="@Weight">
                                                                <xsl:value-of select="." />
                                                            </xsl:for-each>
                                                        </td>
                                                        <td width="85%">
                                                            <table border="1" frame="void">
                                                                <thead>
                                                                    <tr bgcolor="#FFA851">
                                                                        <td>
                                                                            <span style="font-weight:bold; ">基本因子</span>
                                                                        </td>
                                                                        <td>
                                                                            <span style="font-weight:bold; ">权重</span>
                                                                        </td>
                                                                        <td width="10%">
                                                                            <span style="font-weight:bold; ">计算公式</span>
                                                                        </td>
                                                                        <td>
                                                                            <span style="font-weight:bold; ">地图数据</span>
                                                                        </td>
                                                                        <td />
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <xsl:for-each select="n1:BasicFacet">
                                                                        <tr>
                                                                            <td>
                                                                                <xsl:for-each select="@Name">
                                                                                    <xsl:value-of select="." />
                                                                                </xsl:for-each>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:for-each select="@Weight">
                                                                                    <xsl:value-of select="." />
                                                                                </xsl:for-each>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@Formula = 0">
                                                                                        <xsl:for-each select="@Formula">指数衰减</xsl:for-each>
                                                                                    </xsl:when>
                                                                                    <xsl:when test="@Formula = 1">
                                                                                        <xsl:for-each select="@Formula">线性衰减</xsl:for-each>
                                                                                    </xsl:when>
                                                                                </xsl:choose>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:for-each select="n1:DataPath">
                                                                                    <xsl:apply-templates />
                                                                                </xsl:for-each>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test="n1:DerivedFacet">
                                                                                    <table border="1" frame="void">
                                                                                        <thead>
                                                                                            <tr bgcolor="#FFC488">
                                                                                                <td>
                                                                                                    <span style="font-weight:bold; ">派生因子</span>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <span style="font-weight:bold; ">权重</span>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <span style="font-weight:bold; ">计算公式</span>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <span style="font-weight:bold; ">地图数据</span>
                                                                                                </td>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody>
                                                                                            <xsl:for-each select="n1:DerivedFacet">
                                                                                                <tr>
                                                                                                    <td>
                                                                                                        <xsl:for-each select="@Name">
                                                                                                            <xsl:value-of select="." />
                                                                                                        </xsl:for-each>
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        <xsl:for-each select="@Weight">
                                                                                                            <xsl:value-of select="." />
                                                                                                        </xsl:for-each>
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        <xsl:choose>
                                                                                                            <xsl:when test="@Formula  = 0">
                                                                                                                <xsl:for-each select="@Formula">指数衰减</xsl:for-each>
                                                                                                            </xsl:when>
                                                                                                            <xsl:when test="@Formula  = 1">
                                                                                                                <xsl:for-each select="@Formula">线性衰减</xsl:for-each>
                                                                                                            </xsl:when>
                                                                                                        </xsl:choose>
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        <xsl:for-each select="n1:DataPath">
                                                                                                            <xsl:apply-templates />
                                                                                                        </xsl:for-each>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </xsl:for-each>
                                                                                        </tbody>
                                                                                    </table>
                                                                                </xsl:if>
                                                                            </td>
                                                                        </tr>
                                                                    </xsl:for-each>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </xsl:for-each>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                    <br />
                    <a>
                        <xsl:attribute name="href"><xsl:text disable-output-escaping="yes">#top</xsl:text></xsl:attribute>回到顶端</a>
                    <br />
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
