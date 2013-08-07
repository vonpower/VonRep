<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="http://my.opera.com/VonPower/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes">
    <xsl:output version="1.0" encoding="ISO-8859-1" indent="no" method="text" omit-xml-declaration="yes" media-type="text/rtf" />
    <xsl:template match="/">
        <xsl:param name="tablewidth" select="10511" />
        <xsl:param name="globaltablelevel" select="0" />
        <xsl:text>{\rtf1\ansi\ansicpg65001\deff0</xsl:text>
        <xsl:text>{\fonttbl{\f0\fnil Times New Roman;}{\f1\fnil Arial;}{\f2\fnil Symbol;}{\f3\fnil Wingdings;}}</xsl:text>
        <xsl:text>{\colortbl;\red255\green255\blue255;\red0\green0\blue255;\red255\green128\blue64;\red255\green128\blue0;\red255\green168\blue81;\red255\green196\blue136;}</xsl:text>
        <xsl:text>{\stylesheet{\ql \fs24 \snext0 Normal;}{\s1\ql \b\f1\fs48 \snext0 Heading 1;}{\s2\ql \b\f1\fs36 \snext0 Heading 2;}{\s3\ql \b\f1\fs28 \snext0 Heading 3;}{\s4\ql \b\f1\fs24 \snext0 Heading 4;}{\s5\ql \b\f1\fs20 \snext0 Heading 5;}{\s6\ql \b\f1\fs16 \snext0 Heading 6;}{\s7\ql \i\f0\fs24 \snext0 Address;}{\s8\ql \lin720\f0\fs24 \snext0 Blockquote;}{\s9\qc \f0\fs24 \snext0 Center;}}</xsl:text>
        <xsl:text>{\*\generator Altova-Stylevision-2005;}\viewkind1\viewzk2\viewscale75\paperh15840 \paperw12240 \margt1137 \margb1137 \margr864 \margl864 \uc1\pard\fs24</xsl:text>
        <xsl:for-each select="n1:FFTable">
            <xsl:text>{\*\bkmkstart </xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>top</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text>
            <xsl:text>{\*\bkmkend </xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>top</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text><xsl:for-each select="@Name">
                <xsl:text>\pard</xsl:text>
                <xsl:if test="$globaltablelevel + 0 = 1">
                    <xsl:text>\intbl</xsl:text>
                </xsl:if>
                <xsl:if test="$globaltablelevel + 0 > 1">
                    <xsl:text>\itap</xsl:text>
                    <xsl:value-of select="$globaltablelevel + 0" />
                </xsl:if>
                <xsl:text>{</xsl:text>
                <xsl:call-template name="write-text">
                    <xsl:with-param name="text">
                        <xsl:value-of select="." />
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:text>}</xsl:text>
                <xsl:text>\pard</xsl:text>
                <xsl:if test="$globaltablelevel + 0 = 1">
                    <xsl:text>\intbl</xsl:text>
                </xsl:if>
                <xsl:if test="$globaltablelevel + 0 > 1">
                    <xsl:text>\itap</xsl:text>
                    <xsl:value-of select="$globaltablelevel + 0" />
                </xsl:if>
                <xsl:text>{</xsl:text>
                <xsl:call-template name="write-text">
                    <xsl:with-param name="text">
                        <xsl:text>因素因子权重值赋值表</xsl:text>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:text>}</xsl:text>
            </xsl:for-each>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text>\pard<xsl:if test="$globaltablelevel + 0=1">\intbl</xsl:if><xsl:if test="$globaltablelevel + 0>1">\itap<xsl:value-of select="$globaltablelevel + 0"/></xsl:if>{\par}{\shp{\*\shpinst\shpbypara\shpleft0\shptop-120\shpright<xsl:value-of select="$tablewidth"/>\shpbottom-120\shpfhdr0\shpwr3{\sp{\sn shapeType}{\sv 20}}{\sp{\sn posrelv}{\sv 3}}{\sp{\sn lineDashing}{\sv 0}}{\sp{\sn lineWidth}{\sv 36000}}{\sp{\sn lineColor}{\sv 8388608}}<xsl:if test="$globaltablelevel + 0>0">{\sp{\sn fLayoutInCell}{\sv 1}}</xsl:if>}}\pard<xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{</xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text> </xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text><xsl:variable name="borderwidth1" select="0"/><xsl:variable name="cellwidth1_1" select="(ceiling($tablewidth * 10 div 100) - 0- $borderwidth1)"/><xsl:variable name="cellwidth1_2" select="(ceiling($tablewidth * 5 div 100) - 0- $borderwidth1)"/><xsl:variable name="cellwidth1_3" select="(ceiling($tablewidth * 85 div 100) - 0- $borderwidth1)"/><xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 1 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 1 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 1" />
            </xsl:if>
            <xsl:text>{\b </xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>因素类别</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 1=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 1 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 1 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 1" />
            </xsl:if>
            <xsl:text>{\b </xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>权重</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 1=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 1=1">\pard\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\pard\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 1=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 1>1">\pard\itap<xsl:value-of select="$globaltablelevel + 1"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trhdr\trbrdrt\brdroutset\brdrw15 \trbrdrl\brdroutset\brdrw15 \trbrdrb\brdroutset\brdrw15 \trbrdrr\brdroutset\brdrw15 \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat3\cellx<xsl:value-of select="ceiling( $cellwidth1_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat3\cellx<xsl:value-of select="ceiling( $cellwidth1_1 + $cellwidth1_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat3\cellx<xsl:value-of select="ceiling( $cellwidth1_1 + $cellwidth1_2 + $cellwidth1_3 )"/> <xsl:if test="$globaltablelevel + 1=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 1>1">\nestrow}{\nonesttables\par }}\pard</xsl:if><xsl:for-each select="n1:FactorCatalog"><xsl:variable name="borderwidth2" select="0"/><xsl:variable name="cellwidth2_1" select="(ceiling($tablewidth * 10 div 100) - 0- $borderwidth2)"/><xsl:variable name="cellwidth2_2" select="(ceiling($tablewidth * 5 div 100) - 0- $borderwidth2)"/><xsl:variable name="cellwidth2_3" select="(ceiling($tablewidth * 85 div 100) - 0- $borderwidth2)"/><xsl:for-each select="@Name">
                    <xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 1 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 1 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 1" />
                    </xsl:if>
                    <xsl:text>{</xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:value-of select="." />
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text>
                </xsl:for-each><xsl:if test="$globaltablelevel + 1=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="@Weight">
                    <xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 1 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 1 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 1" />
                    </xsl:if>
                    <xsl:text>{</xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:value-of select="." />
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text>
                </xsl:for-each><xsl:if test="$globaltablelevel + 1=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:variable name="borderwidth3" select="5"/><xsl:variable name="notspec_cellwidth_helper3" select="($cellwidth2_3 - 0 * 1 - ceiling($cellwidth2_3 * 10 div 100) - ceiling($cellwidth2_3 * 85 div 100)) div 1"/><xsl:variable name="notspec_cellwidth3"><xsl:choose><xsl:when test="$notspec_cellwidth_helper3 > 0 "><xsl:value-of select="$notspec_cellwidth_helper3"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth3_1" select="(ceiling($cellwidth2_3 * 10 div 100) - 0- $borderwidth3)"/><xsl:variable name="cellwidth3_2" select="($notspec_cellwidth3 - 0- $borderwidth3)"/><xsl:variable name="cellwidth3_3" select="(ceiling($cellwidth2_3 * 85 div 100) - 0- $borderwidth3)"/><xsl:text>\pard</xsl:text>
                <xsl:if test="$globaltablelevel + 2 = 1">
                    <xsl:text>\intbl</xsl:text>
                </xsl:if>
                <xsl:if test="$globaltablelevel + 2 > 1">
                    <xsl:text>\itap</xsl:text>
                    <xsl:value-of select="$globaltablelevel + 2" />
                </xsl:if>
                <xsl:text>{\b </xsl:text>
                <xsl:call-template name="write-text">
                    <xsl:with-param name="text">
                        <xsl:text>因素</xsl:text>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 2=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                <xsl:if test="$globaltablelevel + 2 = 1">
                    <xsl:text>\intbl</xsl:text>
                </xsl:if>
                <xsl:if test="$globaltablelevel + 2 > 1">
                    <xsl:text>\itap</xsl:text>
                    <xsl:value-of select="$globaltablelevel + 2" />
                </xsl:if>
                <xsl:text>{\b </xsl:text>
                <xsl:call-template name="write-text">
                    <xsl:with-param name="text">
                        <xsl:text>权重</xsl:text>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 2=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 2=1">\pard\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\pard\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 2=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 2>1">\pard\itap<xsl:value-of select="$globaltablelevel + 2"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trhdr\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat4\cellx<xsl:value-of select="ceiling( $cellwidth3_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat4\cellx<xsl:value-of select="ceiling( $cellwidth3_1 + $cellwidth3_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat4\cellx<xsl:value-of select="ceiling( $cellwidth3_1 + $cellwidth3_2 + $cellwidth3_3 )"/> <xsl:if test="$globaltablelevel + 2=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 2>1">\nestrow}{\nonesttables\par }}\pard</xsl:if><xsl:for-each select="n1:BasicFactor"><xsl:variable name="borderwidth4" select="5"/><xsl:variable name="notspec_cellwidth_helper4" select="($cellwidth2_3 - 0 * 1 - ceiling($cellwidth2_3 * 10 div 100) - ceiling($cellwidth2_3 * 85 div 100)) div 1"/><xsl:variable name="notspec_cellwidth4"><xsl:choose><xsl:when test="$notspec_cellwidth_helper4 > 0 "><xsl:value-of select="$notspec_cellwidth_helper4"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth4_1" select="(ceiling($cellwidth2_3 * 10 div 100) - 0- $borderwidth4)"/><xsl:variable name="cellwidth4_2" select="($notspec_cellwidth4 - 0- $borderwidth4)"/><xsl:variable name="cellwidth4_3" select="(ceiling($cellwidth2_3 * 85 div 100) - 0- $borderwidth4)"/><xsl:for-each select="@Name">
                        <xsl:text>\pard</xsl:text>
                        <xsl:if test="$globaltablelevel + 2 = 1">
                            <xsl:text>\intbl</xsl:text>
                        </xsl:if>
                        <xsl:if test="$globaltablelevel + 2 > 1">
                            <xsl:text>\itap</xsl:text>
                            <xsl:value-of select="$globaltablelevel + 2" />
                        </xsl:if>
                        <xsl:text>{</xsl:text>
                        <xsl:call-template name="write-text">
                            <xsl:with-param name="text">
                                <xsl:value-of select="." />
                            </xsl:with-param>
                        </xsl:call-template>
                        <xsl:text>}</xsl:text>
                    </xsl:for-each><xsl:if test="$globaltablelevel + 2=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="@Weight">
                        <xsl:text>\pard</xsl:text>
                        <xsl:if test="$globaltablelevel + 2 = 1">
                            <xsl:text>\intbl</xsl:text>
                        </xsl:if>
                        <xsl:if test="$globaltablelevel + 2 > 1">
                            <xsl:text>\itap</xsl:text>
                            <xsl:value-of select="$globaltablelevel + 2" />
                        </xsl:if>
                        <xsl:text>{</xsl:text>
                        <xsl:call-template name="write-text">
                            <xsl:with-param name="text">
                                <xsl:value-of select="." />
                            </xsl:with-param>
                        </xsl:call-template>
                        <xsl:text>}</xsl:text>
                    </xsl:for-each><xsl:if test="$globaltablelevel + 2=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:variable name="borderwidth5" select="3"/><xsl:variable name="notspec_cellwidth_helper5" select="($cellwidth4_3 - 0 * 4 - ceiling($cellwidth4_3 * 10 div 100)) div 4"/><xsl:variable name="notspec_cellwidth5"><xsl:choose><xsl:when test="$notspec_cellwidth_helper5 > 0 "><xsl:value-of select="$notspec_cellwidth_helper5"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth5_1" select="($notspec_cellwidth5 - 0- $borderwidth5)"/><xsl:variable name="cellwidth5_2" select="($notspec_cellwidth5 - 0- $borderwidth5)"/><xsl:variable name="cellwidth5_3" select="(ceiling($cellwidth4_3 * 10 div 100) - 0- $borderwidth5)"/><xsl:variable name="cellwidth5_4" select="($notspec_cellwidth5 - 0- $borderwidth5)"/><xsl:variable name="cellwidth5_5" select="($notspec_cellwidth5 - 0- $borderwidth5)"/><xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 3 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 3 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 3" />
                    </xsl:if>
                    <xsl:text>{\b </xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:text>基本因子</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 3 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 3 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 3" />
                    </xsl:if>
                    <xsl:text>{\b </xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:text>权重</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 3 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 3 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 3" />
                    </xsl:if>
                    <xsl:text>{\b </xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:text>计算公式</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                    <xsl:if test="$globaltablelevel + 3 = 1">
                        <xsl:text>\intbl</xsl:text>
                    </xsl:if>
                    <xsl:if test="$globaltablelevel + 3 > 1">
                        <xsl:text>\itap</xsl:text>
                        <xsl:value-of select="$globaltablelevel + 3" />
                    </xsl:if>
                    <xsl:text>{\b </xsl:text>
                    <xsl:call-template name="write-text">
                        <xsl:with-param name="text">
                            <xsl:text>地图数据</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 3=1">\pard\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\pard\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 3=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 3>1">\pard\itap<xsl:value-of select="$globaltablelevel + 3"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trhdr\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat5\cellx<xsl:value-of select="ceiling( $cellwidth5_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat5\cellx<xsl:value-of select="ceiling( $cellwidth5_1 + $cellwidth5_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat5\cellx<xsl:value-of select="ceiling( $cellwidth5_1 + $cellwidth5_2 + $cellwidth5_3 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat5\cellx<xsl:value-of select="ceiling( $cellwidth5_1 + $cellwidth5_2 + $cellwidth5_3 + $cellwidth5_4 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat5\cellx<xsl:value-of select="ceiling( $cellwidth5_1 + $cellwidth5_2 + $cellwidth5_3 + $cellwidth5_4 + $cellwidth5_5 )"/> <xsl:if test="$globaltablelevel + 3=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 3>1">\nestrow}{\nonesttables\par }}\pard</xsl:if><xsl:for-each select="n1:BasicFacet"><xsl:variable name="borderwidth6" select="3"/><xsl:variable name="notspec_cellwidth_helper6" select="($cellwidth4_3 - 0 * 5) div 5"/><xsl:variable name="notspec_cellwidth6"><xsl:choose><xsl:when test="$notspec_cellwidth_helper6 > 0 "><xsl:value-of select="$notspec_cellwidth_helper6"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth6_1" select="($notspec_cellwidth6 - 0- $borderwidth6)"/><xsl:variable name="cellwidth6_2" select="($notspec_cellwidth6 - 0- $borderwidth6)"/><xsl:variable name="cellwidth6_3" select="($notspec_cellwidth6 - 0- $borderwidth6)"/><xsl:variable name="cellwidth6_4" select="($notspec_cellwidth6 - 0- $borderwidth6)"/><xsl:variable name="cellwidth6_5" select="($notspec_cellwidth6 - 0- $borderwidth6)"/><xsl:for-each select="@Name">
                            <xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 3 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 3 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 3" />
                            </xsl:if>
                            <xsl:text>{</xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:value-of select="." />
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text>
                        </xsl:for-each><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="@Weight">
                            <xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 3 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 3 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 3" />
                            </xsl:if>
                            <xsl:text>{</xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:value-of select="." />
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text>
                        </xsl:for-each><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:choose>
                            <xsl:when test="@Formula = 0">
                                <xsl:for-each select="@Formula">
                                    <xsl:text>\pard</xsl:text>
                                    <xsl:if test="$globaltablelevel + 3 = 1">
                                        <xsl:text>\intbl</xsl:text>
                                    </xsl:if>
                                    <xsl:if test="$globaltablelevel + 3 > 1">
                                        <xsl:text>\itap</xsl:text>
                                        <xsl:value-of select="$globaltablelevel + 3" />
                                    </xsl:if>
                                    <xsl:text>{</xsl:text>
                                    <xsl:call-template name="write-text">
                                        <xsl:with-param name="text">
                                            <xsl:text>指数衰减</xsl:text>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                    <xsl:text>}</xsl:text>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:when test="@Formula = 1">
                                <xsl:for-each select="@Formula">
                                    <xsl:text>\pard</xsl:text>
                                    <xsl:if test="$globaltablelevel + 3 = 1">
                                        <xsl:text>\intbl</xsl:text>
                                    </xsl:if>
                                    <xsl:if test="$globaltablelevel + 3 > 1">
                                        <xsl:text>\itap</xsl:text>
                                        <xsl:value-of select="$globaltablelevel + 3" />
                                    </xsl:if>
                                    <xsl:text>{</xsl:text>
                                    <xsl:call-template name="write-text">
                                        <xsl:with-param name="text">
                                            <xsl:text>线性衰减</xsl:text>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                    <xsl:text>}</xsl:text>
                                </xsl:for-each>
                            </xsl:when>
                        </xsl:choose><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="n1:DataPath">
                            <xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 3 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 3 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 3" />
                            </xsl:if>
                            <xsl:text>{</xsl:text>
                            <xsl:apply-templates />
                            <xsl:text>}</xsl:text>
                        </xsl:for-each><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="n1:DerivedFacet"><xsl:variable name="borderwidth7" select="3"/><xsl:variable name="notspec_cellwidth_helper7" select="($cellwidth6_5 - 0 * 4) div 4"/><xsl:variable name="notspec_cellwidth7"><xsl:choose><xsl:when test="$notspec_cellwidth_helper7 > 0 "><xsl:value-of select="$notspec_cellwidth_helper7"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth7_1" select="($notspec_cellwidth7 - 0- $borderwidth7)"/><xsl:variable name="cellwidth7_2" select="($notspec_cellwidth7 - 0- $borderwidth7)"/><xsl:variable name="cellwidth7_3" select="($notspec_cellwidth7 - 0- $borderwidth7)"/><xsl:variable name="cellwidth7_4" select="($notspec_cellwidth7 - 0- $borderwidth7)"/><xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 4 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 4 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 4" />
                            </xsl:if>
                            <xsl:text>{\b </xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:text>派生因子</xsl:text>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 4 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 4 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 4" />
                            </xsl:if>
                            <xsl:text>{\b </xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:text>权重</xsl:text>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 4 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 4 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 4" />
                            </xsl:if>
                            <xsl:text>{\b </xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:text>计算公式</xsl:text>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:text>\pard</xsl:text>
                            <xsl:if test="$globaltablelevel + 4 = 1">
                                <xsl:text>\intbl</xsl:text>
                            </xsl:if>
                            <xsl:if test="$globaltablelevel + 4 > 1">
                                <xsl:text>\itap</xsl:text>
                                <xsl:value-of select="$globaltablelevel + 4" />
                            </xsl:if>
                            <xsl:text>{\b </xsl:text>
                            <xsl:call-template name="write-text">
                                <xsl:with-param name="text">
                                    <xsl:text>地图数据</xsl:text>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>}</xsl:text><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 4=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 4>1">\pard\itap<xsl:value-of select="$globaltablelevel + 4"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trhdr\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat6\cellx<xsl:value-of select="ceiling( $cellwidth7_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat6\cellx<xsl:value-of select="ceiling( $cellwidth7_1 + $cellwidth7_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat6\cellx<xsl:value-of select="ceiling( $cellwidth7_1 + $cellwidth7_2 + $cellwidth7_3 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \clcbpat6\cellx<xsl:value-of select="ceiling( $cellwidth7_1 + $cellwidth7_2 + $cellwidth7_3 + $cellwidth7_4 )"/> <xsl:if test="$globaltablelevel + 4=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 4>1">\nestrow}{\nonesttables\par }}\pard</xsl:if><xsl:for-each select="n1:DerivedFacet"><xsl:variable name="borderwidth8" select="3"/><xsl:variable name="notspec_cellwidth_helper8" select="($cellwidth6_5 - 0 * 4) div 4"/><xsl:variable name="notspec_cellwidth8"><xsl:choose><xsl:when test="$notspec_cellwidth_helper8 > 0 "><xsl:value-of select="$notspec_cellwidth_helper8"/></xsl:when><xsl:otherwise>1</xsl:otherwise></xsl:choose></xsl:variable><xsl:variable name="cellwidth8_1" select="($notspec_cellwidth8 - 0- $borderwidth8)"/><xsl:variable name="cellwidth8_2" select="($notspec_cellwidth8 - 0- $borderwidth8)"/><xsl:variable name="cellwidth8_3" select="($notspec_cellwidth8 - 0- $borderwidth8)"/><xsl:variable name="cellwidth8_4" select="($notspec_cellwidth8 - 0- $borderwidth8)"/><xsl:for-each select="@Name">
                                    <xsl:text>\pard</xsl:text>
                                    <xsl:if test="$globaltablelevel + 4 = 1">
                                        <xsl:text>\intbl</xsl:text>
                                    </xsl:if>
                                    <xsl:if test="$globaltablelevel + 4 > 1">
                                        <xsl:text>\itap</xsl:text>
                                        <xsl:value-of select="$globaltablelevel + 4" />
                                    </xsl:if>
                                    <xsl:text>{</xsl:text>
                                    <xsl:call-template name="write-text">
                                        <xsl:with-param name="text">
                                            <xsl:value-of select="." />
                                        </xsl:with-param>
                                    </xsl:call-template>
                                    <xsl:text>}</xsl:text>
                                </xsl:for-each><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="@Weight">
                                    <xsl:text>\pard</xsl:text>
                                    <xsl:if test="$globaltablelevel + 4 = 1">
                                        <xsl:text>\intbl</xsl:text>
                                    </xsl:if>
                                    <xsl:if test="$globaltablelevel + 4 > 1">
                                        <xsl:text>\itap</xsl:text>
                                        <xsl:value-of select="$globaltablelevel + 4" />
                                    </xsl:if>
                                    <xsl:text>{</xsl:text>
                                    <xsl:call-template name="write-text">
                                        <xsl:with-param name="text">
                                            <xsl:value-of select="." />
                                        </xsl:with-param>
                                    </xsl:call-template>
                                    <xsl:text>}</xsl:text>
                                </xsl:for-each><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:choose>
                                    <xsl:when test="@Formula  = 0">
                                        <xsl:for-each select="@Formula">
                                            <xsl:text>\pard</xsl:text>
                                            <xsl:if test="$globaltablelevel + 4 = 1">
                                                <xsl:text>\intbl</xsl:text>
                                            </xsl:if>
                                            <xsl:if test="$globaltablelevel + 4 > 1">
                                                <xsl:text>\itap</xsl:text>
                                                <xsl:value-of select="$globaltablelevel + 4" />
                                            </xsl:if>
                                            <xsl:text>{</xsl:text>
                                            <xsl:call-template name="write-text">
                                                <xsl:with-param name="text">
                                                    <xsl:text>指数衰减</xsl:text>
                                                </xsl:with-param>
                                            </xsl:call-template>
                                            <xsl:text>}</xsl:text>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:when test="@Formula  = 1">
                                        <xsl:for-each select="@Formula">
                                            <xsl:text>\pard</xsl:text>
                                            <xsl:if test="$globaltablelevel + 4 = 1">
                                                <xsl:text>\intbl</xsl:text>
                                            </xsl:if>
                                            <xsl:if test="$globaltablelevel + 4 > 1">
                                                <xsl:text>\itap</xsl:text>
                                                <xsl:value-of select="$globaltablelevel + 4" />
                                            </xsl:if>
                                            <xsl:text>{</xsl:text>
                                            <xsl:call-template name="write-text">
                                                <xsl:with-param name="text">
                                                    <xsl:text>线性衰减</xsl:text>
                                                </xsl:with-param>
                                            </xsl:call-template>
                                            <xsl:text>}</xsl:text>
                                        </xsl:for-each>
                                    </xsl:when>
                                </xsl:choose><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:for-each select="n1:DataPath">
                                    <xsl:text>\pard</xsl:text>
                                    <xsl:if test="$globaltablelevel + 4 = 1">
                                        <xsl:text>\intbl</xsl:text>
                                    </xsl:if>
                                    <xsl:if test="$globaltablelevel + 4 > 1">
                                        <xsl:text>\itap</xsl:text>
                                        <xsl:value-of select="$globaltablelevel + 4" />
                                    </xsl:if>
                                    <xsl:text>{</xsl:text>
                                    <xsl:apply-templates />
                                    <xsl:text>}</xsl:text>
                                </xsl:for-each><xsl:if test="$globaltablelevel + 4=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 4>1">\itap<xsl:value-of select="$globaltablelevel + 4"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 4=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 4>1">\pard\itap<xsl:value-of select="$globaltablelevel + 4"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth8_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth8_1 + $cellwidth8_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth8_1 + $cellwidth8_2 + $cellwidth8_3 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth8_1 + $cellwidth8_2 + $cellwidth8_3 + $cellwidth8_4 )"/> <xsl:if test="$globaltablelevel + 4=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 4>1">\nestrow}{\nonesttables\par }}\pard</xsl:if></xsl:for-each>
                        </xsl:if><xsl:if test="$globaltablelevel + 3=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 3>1">\itap<xsl:value-of select="$globaltablelevel + 3"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 3=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 3>1">\pard\itap<xsl:value-of select="$globaltablelevel + 3"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth6_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth6_1 + $cellwidth6_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth6_1 + $cellwidth6_2 + $cellwidth6_3 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth6_1 + $cellwidth6_2 + $cellwidth6_3 + $cellwidth6_4 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth6_1 + $cellwidth6_2 + $cellwidth6_3 + $cellwidth6_4 + $cellwidth6_5 )"/> <xsl:if test="$globaltablelevel + 3=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 3>1">\nestrow}{\nonesttables\par }}\pard</xsl:if></xsl:for-each><xsl:if test="$globaltablelevel + 2=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 2>1">\itap<xsl:value-of select="$globaltablelevel + 2"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 2=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 2>1">\pard\itap<xsl:value-of select="$globaltablelevel + 2"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trbrdrt\brdrtbl \trbrdrl\brdrtbl \trbrdrb\brdrtbl \trbrdrr\brdrtbl \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth4_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth4_1 + $cellwidth4_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth4_1 + $cellwidth4_2 + $cellwidth4_3 )"/> <xsl:if test="$globaltablelevel + 2=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 2>1">\nestrow}{\nonesttables\par }}\pard</xsl:if></xsl:for-each><xsl:if test="$globaltablelevel + 1=1">\intbl{\cell }</xsl:if><xsl:if test="$globaltablelevel + 1>1">\itap<xsl:value-of select="$globaltablelevel + 1"/>{{\nestcell}{\nonesttables\par }}</xsl:if><xsl:if test="$globaltablelevel + 1=1">\pard\intbl{</xsl:if><xsl:if test="$globaltablelevel + 1>1">\pard\itap<xsl:value-of select="$globaltablelevel + 1"/> {{\*\nesttableprops</xsl:if>\trowd\trrh0\trbrdrt\brdroutset\brdrw15 \trbrdrl\brdroutset\brdrw15 \trbrdrb\brdroutset\brdrw15 \trbrdrr\brdroutset\brdrw15 \trspdl0\trspdt0\trspdb0\trspdr0\trspdfl3\trspdft3\trspdfb3\trspdfr3\tbllkhdrrows\tbllklastrow\tbllkhdrcols\tbllklastcol \clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth2_1 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth2_1 + $cellwidth2_2 )"/>\clvertalc\clbrdrt\brdroutset\brdrw15 \clbrdrl\brdroutset\brdrw15 \clbrdrb\brdroutset\brdrw15 \clbrdrr\brdroutset\brdrw15 \cellx<xsl:value-of select="ceiling( $cellwidth2_1 + $cellwidth2_2 + $cellwidth2_3 )"/> <xsl:if test="$globaltablelevel + 1=1">\row}\pard</xsl:if><xsl:if test="$globaltablelevel + 1>1">\nestrow}{\nonesttables\par }}\pard</xsl:if></xsl:for-each>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\field{\*\fldinst { HYPERLINK \\l"</xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>top</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>" }}{\fldrslt </xsl:text>
            <xsl:text>{\ul \cf2 </xsl:text>
            <xsl:call-template name="write-text">
                <xsl:with-param name="text">
                    <xsl:text>回到顶端</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:text>}</xsl:text>
            <xsl:text>}}</xsl:text>
            <xsl:text>\pard</xsl:text>
            <xsl:if test="$globaltablelevel + 0 = 1">
                <xsl:text>\intbl</xsl:text>
            </xsl:if>
            <xsl:if test="$globaltablelevel + 0 > 1">
                <xsl:text>\itap</xsl:text>
                <xsl:value-of select="$globaltablelevel + 0" />
            </xsl:if>
            <xsl:text>{\par}</xsl:text>
        </xsl:for-each>
        <xsl:text>}</xsl:text>
    </xsl:template>
	<xsl:template name="write-text">
		<xsl:param name="text"/>
		<xsl:variable name="txt" select="string-join($text,'')"/>
			<xsl:value-of select="string-join(for $i in string-to-codepoints($txt) return if ($i &lt; 32) then ' ' else concat('\u',string($i),'?'),'')"/>
	</xsl:template>
	<xsl:template match="text() |@*">
		<xsl:call-template name="write-text">
			<xsl:with-param name="text" select="."/>
		</xsl:call-template>
	</xsl:template>
</xsl:stylesheet>
