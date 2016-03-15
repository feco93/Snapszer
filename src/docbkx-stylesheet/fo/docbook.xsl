<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
>

    <!-- imports the original docbook stylesheet -->
    <xsl:import href="urn:docbkx:stylesheet"/>
    <xsl:import href="mytitlepage.xsl"/>


    <!--
    *************************************************************
    Fejezet feliratok kikapcsolása
    *************************************************************
    -->
    <xsl:param name="chapter.autolabel">0</xsl:param>

    <!--
    *************************************************************
    Oldalszámozás beállítása
    *************************************************************
    -->

    <xsl:template name="initial.page.number">
        <xsl:param name="element" select="local-name(.)"/>
        <xsl:choose>
            <xsl:when test="$element = 'toc'">1</xsl:when>
            <xsl:otherwise>auto</xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="page.number.format">
        <xsl:param name="element" select="local-name(.)"/>
        <xsl:choose>
            <xsl:when test="$element = 'toc'">1</xsl:when>
            <xsl:when test="$element = 'chapter'">1</xsl:when>
        </xsl:choose>
    </xsl:template>

    <!--
    *************************************************************
    Betűtípus/betűméret beállítása
    *************************************************************
    -->

    <xsl:param name="body.font.family">Times New Roman</xsl:param>
    <xsl:param name="title.font.family">Times New Roman</xsl:param>
    <xsl:param name="body.font.master">12</xsl:param>

    <!--
    *************************************************************
    Margók beállítása
    *************************************************************
    -->

    <xsl:param name="page.margin.inner">3cm</xsl:param>
    <xsl:param name="page.margin.outer">2cm</xsl:param>
    <xsl:param name="page.margin.bottom">3cm</xsl:param>
    <xsl:param name="page.margin.top">3cm</xsl:param>

    <!--
    *************************************************************
    Behúzások beállítása
    *************************************************************
    -->
    <xsl:param name="body.start.indent">0pt</xsl:param>
    <xsl:param name="body.end.indent">0pt</xsl:param>

    <xsl:attribute-set name="monospace.verbatim.properties">
        <xsl:attribute name="wrap-option">wrap</xsl:attribute>
        <xsl:attribute name="font-size">10</xsl:attribute>
    </xsl:attribute-set>

</xsl:stylesheet>