<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:n1="http://my.opera.com/VonPower/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes">
    <xsl:variable name="fo:layout-master-set">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="default-page" page-height="11in" page-width="8.5in" margin-left="0.6in" margin-right="0.6in">
                <fo:region-body margin-top="0.79in" margin-bottom="0.79in" />
            </fo:simple-page-master>
        </fo:layout-master-set>
    </xsl:variable>
    <xsl:output version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="no" media-type="text/html" />
    <xsl:template match="/">
        <fo:root>
            <xsl:copy-of select="$fo:layout-master-set" />
            <fo:page-sequence master-reference="default-page" initial-page-number="1" format="1">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:for-each select="n1:FFTable">
                            <fo:inline border-bottom-color="#000080" border-bottom-style="solid" border-bottom-width="medium">
                                <xsl:attribute name="id"><xsl:text disable-output-escaping="yes">top</xsl:text></xsl:attribute>
                            </fo:inline>
                            <fo:block space-before.optimum="1pt" space-after.optimum="2pt">
                                <fo:block border-bottom-color="#000080" border-bottom-style="solid" border-bottom-width="medium">
                                    <xsl:for-each select="@Name">
                                        <xsl:value-of select="." />因素因子权重值赋值表</xsl:for-each>
                                    <fo:block>
                                        <fo:leader leader-pattern="space" />
                                    </fo:block>
                                </fo:block>
                            </fo:block>&#160;<fo:table width="100%" space-before.optimum="1pt" space-after.optimum="2pt">
                                <fo:table-column column-width="proportional-column-width(10)" />
                                <fo:table-column column-width="proportional-column-width(5)" />
                                <fo:table-column column-width="proportional-column-width(85)" />
                                <fo:table-header>
                                    <fo:table-row background-color="#FF8040">
                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="10%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                            <fo:block>
                                                <fo:inline font-weight="bold">因素类别</fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="5%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                            <fo:block>
                                                <fo:inline font-weight="bold">权重</fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="85%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                            <fo:block />
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-header>
                                <fo:table-body>
                                    <xsl:for-each select="n1:FactorCatalog">
                                        <fo:table-row>
                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="10%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                <fo:block>
                                                    <xsl:for-each select="@Name">
                                                        <xsl:value-of select="." />
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="5%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                <fo:block>
                                                    <xsl:for-each select="@Weight">
                                                        <xsl:value-of select="." />
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="85%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                <fo:block>
                                                    <fo:table width="100%" space-before.optimum="1pt" space-after.optimum="2pt">
                                                        <fo:table-column column-width="proportional-column-width(10)" />
                                                        <fo:table-column />
                                                        <fo:table-column column-width="proportional-column-width(85)" />
                                                        <fo:table-header>
                                                            <fo:table-row background-color="#FF8000" width="5%">
                                                                <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="10%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                    <fo:block>
                                                                        <fo:inline font-weight="bold">因素</fo:inline>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                    <fo:block>
                                                                        <fo:inline font-weight="bold">权重</fo:inline>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="85%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                    <fo:block />
                                                                </fo:table-cell>
                                                            </fo:table-row>
                                                        </fo:table-header>
                                                        <fo:table-body>
                                                            <xsl:for-each select="n1:BasicFactor">
                                                                <fo:table-row>
                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="10%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                        <fo:block>
                                                                            <xsl:for-each select="@Name">
                                                                                <xsl:value-of select="." />
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:table-cell>
                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                        <fo:block>
                                                                            <xsl:for-each select="@Weight">
                                                                                <xsl:value-of select="." />
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:table-cell>
                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="85%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                        <fo:block>
                                                                            <fo:table width="100%" space-before.optimum="1pt" space-after.optimum="2pt">
                                                                                <fo:table-column />
                                                                                <fo:table-column />
                                                                                <fo:table-column column-width="proportional-column-width(10)" />
                                                                                <fo:table-column />
                                                                                <fo:table-column />
                                                                                <fo:table-header>
                                                                                    <fo:table-row background-color="#FFA851">
                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                            <fo:block>
                                                                                                <fo:inline font-weight="bold">基本因子</fo:inline>
                                                                                            </fo:block>
                                                                                        </fo:table-cell>
                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                            <fo:block>
                                                                                                <fo:inline font-weight="bold">权重</fo:inline>
                                                                                            </fo:block>
                                                                                        </fo:table-cell>
                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" width="10%" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                            <fo:block>
                                                                                                <fo:inline font-weight="bold">计算公式</fo:inline>
                                                                                            </fo:block>
                                                                                        </fo:table-cell>
                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                            <fo:block>
                                                                                                <fo:inline font-weight="bold">地图数据</fo:inline>
                                                                                            </fo:block>
                                                                                        </fo:table-cell>
                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                            <fo:block />
                                                                                        </fo:table-cell>
                                                                                    </fo:table-row>
                                                                                </fo:table-header>
                                                                                <fo:table-body>
                                                                                    <xsl:for-each select="n1:BasicFacet">
                                                                                        <fo:table-row>
                                                                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                <fo:block>
                                                                                                    <xsl:for-each select="@Name">
                                                                                                        <xsl:value-of select="." />
                                                                                                    </xsl:for-each>
                                                                                                </fo:block>
                                                                                            </fo:table-cell>
                                                                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                <fo:block>
                                                                                                    <xsl:for-each select="@Weight">
                                                                                                        <xsl:value-of select="." />
                                                                                                    </xsl:for-each>
                                                                                                </fo:block>
                                                                                            </fo:table-cell>
                                                                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                <fo:block>
                                                                                                    <xsl:choose>
                                                                                                        <xsl:when test="@Formula = 0">
                                                                                                            <xsl:for-each select="@Formula">指数衰减</xsl:for-each>
                                                                                                        </xsl:when>
                                                                                                        <xsl:when test="@Formula = 1">
                                                                                                            <xsl:for-each select="@Formula">线性衰减</xsl:for-each>
                                                                                                        </xsl:when>
                                                                                                    </xsl:choose>
                                                                                                </fo:block>
                                                                                            </fo:table-cell>
                                                                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                <fo:block>
                                                                                                    <xsl:for-each select="n1:DataPath">
                                                                                                        <xsl:apply-templates />
                                                                                                    </xsl:for-each>
                                                                                                </fo:block>
                                                                                            </fo:table-cell>
                                                                                            <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                <fo:block>
                                                                                                    <xsl:if test="n1:DerivedFacet">
                                                                                                        <fo:table width="100%" space-before.optimum="1pt" space-after.optimum="2pt">
                                                                                                            <fo:table-column />
                                                                                                            <fo:table-column />
                                                                                                            <fo:table-column />
                                                                                                            <fo:table-column />
                                                                                                            <fo:table-header>
                                                                                                                <fo:table-row background-color="#FFC488">
                                                                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                        <fo:block>
                                                                                                                            <fo:inline font-weight="bold">派生因子</fo:inline>
                                                                                                                        </fo:block>
                                                                                                                    </fo:table-cell>
                                                                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                        <fo:block>
                                                                                                                            <fo:inline font-weight="bold">权重</fo:inline>
                                                                                                                        </fo:block>
                                                                                                                    </fo:table-cell>
                                                                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                        <fo:block>
                                                                                                                            <fo:inline font-weight="bold">计算公式</fo:inline>
                                                                                                                        </fo:block>
                                                                                                                    </fo:table-cell>
                                                                                                                    <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                        <fo:block>
                                                                                                                            <fo:inline font-weight="bold">地图数据</fo:inline>
                                                                                                                        </fo:block>
                                                                                                                    </fo:table-cell>
                                                                                                                </fo:table-row>
                                                                                                            </fo:table-header>
                                                                                                            <fo:table-body>
                                                                                                                <xsl:for-each select="n1:DerivedFacet">
                                                                                                                    <fo:table-row>
                                                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                            <fo:block>
                                                                                                                                <xsl:for-each select="@Name">
                                                                                                                                    <xsl:value-of select="." />
                                                                                                                                </xsl:for-each>
                                                                                                                            </fo:block>
                                                                                                                        </fo:table-cell>
                                                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                            <fo:block>
                                                                                                                                <xsl:for-each select="@Weight">
                                                                                                                                    <xsl:value-of select="." />
                                                                                                                                </xsl:for-each>
                                                                                                                            </fo:block>
                                                                                                                        </fo:table-cell>
                                                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                            <fo:block>
                                                                                                                                <xsl:choose>
                                                                                                                                    <xsl:when test="@Formula  = 0">
                                                                                                                                        <xsl:for-each select="@Formula">指数衰减</xsl:for-each>
                                                                                                                                    </xsl:when>
                                                                                                                                    <xsl:when test="@Formula  = 1">
                                                                                                                                        <xsl:for-each select="@Formula">线性衰减</xsl:for-each>
                                                                                                                                    </xsl:when>
                                                                                                                                </xsl:choose>
                                                                                                                            </fo:block>
                                                                                                                        </fo:table-cell>
                                                                                                                        <fo:table-cell border-style="solid" border-width="1pt" border-color="black" padding-start="3pt" padding-end="3pt" padding-before="3pt" padding-after="3pt" display-align="center" text-align="start">
                                                                                                                            <fo:block>
                                                                                                                                <xsl:for-each select="n1:DataPath">
                                                                                                                                    <xsl:apply-templates />
                                                                                                                                </xsl:for-each>
                                                                                                                            </fo:block>
                                                                                                                        </fo:table-cell>
                                                                                                                    </fo:table-row>
                                                                                                                </xsl:for-each>
                                                                                                            </fo:table-body>
                                                                                                        </fo:table>
                                                                                                    </xsl:if>
                                                                                                </fo:block>
                                                                                            </fo:table-cell>
                                                                                        </fo:table-row>
                                                                                    </xsl:for-each>
                                                                                </fo:table-body>
                                                                            </fo:table>
                                                                        </fo:block>
                                                                    </fo:table-cell>
                                                                </fo:table-row>
                                                            </xsl:for-each>
                                                        </fo:table-body>
                                                    </fo:table>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                            <fo:block>
                                <fo:leader leader-pattern="space" />
                            </fo:block>
                            <fo:basic-link text-decoration="underline" color="blue">
                                <xsl:attribute name="internal-destination"><xsl:text disable-output-escaping="yes">top</xsl:text></xsl:attribute>
                                <fo:inline>回到顶端</fo:inline>
                            </fo:basic-link>
                            <fo:block>
                                <xsl:text>&#xA;</xsl:text>
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
