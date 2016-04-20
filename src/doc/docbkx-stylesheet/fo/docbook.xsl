<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="1.0"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
>

    <!-- imports the original docbook stylesheet  -->
    <xsl:import href="urn:docbkx:stylesheet"/>
    <xsl:import href="mytitlepage.xsl"/>

    <xsl:param name="paper.type">A4</xsl:param>

    <!--
    *************************************************************
    Fejezet feliratok kikapcsolása
    *************************************************************
    -->
    <xsl:param name="chapter.autolabel">0</xsl:param>
    <xsl:param name="table.autolabel">0</xsl:param>

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

    <xsl:param name="symbol.font.family">Symbol</xsl:param>
    <xsl:param name="body.font.family">TimesNewRoman</xsl:param>
    <xsl:param name="title.font.family">TimesNewRoman</xsl:param>
    <xsl:param name="body.font.master">12</xsl:param>

    <xsl:attribute-set name="section.title.level1.properties">
        <xsl:attribute name="font-size">14
        </xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="section.title.level2.properties">
        <xsl:attribute name="font-size">12
        </xsl:attribute>
    </xsl:attribute-set>

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

    <!--
    *************************************************************
    Programcsipet megjelenítés beállítása
    *************************************************************
    -->

    <xsl:attribute-set name="monospace.verbatim.properties">
        <xsl:attribute name="line-height">normal</xsl:attribute>
        <xsl:attribute name="wrap-option">wrap</xsl:attribute>
        <xsl:attribute name="font-size">10</xsl:attribute>
        <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
        <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
        <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
        <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
        <xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
        <xsl:attribute name="space-after.optimum">1.0em</xsl:attribute>
        <xsl:attribute name="space-after.maximum">1.2em</xsl:attribute>

    </xsl:attribute-set>

    <!--
    *************************************************************
    Sortávolság beállítása
    *************************************************************
    -->
    <xsl:attribute-set name="para.properties">
        <xsl:attribute name="line-height">1.8</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="toc.line.properties">
        <xsl:attribute name="line-height">1.6</xsl:attribute>
    </xsl:attribute-set>


    <!--
    *************************************************************
    Felsorolások testreszabása
    *************************************************************
    -->
    <xsl:attribute-set name="list.item.spacing">
        <xsl:attribute name="line-height">1.8</xsl:attribute>
        <xsl:attribute name="space-before.minimum">0.6em</xsl:attribute>
        <xsl:attribute name="space-before.optimum">0.6em</xsl:attribute>
        <xsl:attribute name="space-before.maximum">0.6em</xsl:attribute>
    </xsl:attribute-set>

    <!--
   *************************************************************
   Táblázat automatikus címkézésének eltávolítása
   *************************************************************
   -->

    <xsl:param name="local.l10n.xml" select="document('')"/>
    <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
        <l:l10n language="hu">
            <l:context name="title">
                <l:template name="table" text="%t"/>
            </l:context>
            <l:context name="xref-number-and-title">
                <l:template name="table" text="the table titled &#8220;%t&#8221;"/>
            </l:context>
        </l:l10n>
    </l:i18n>

    <xsl:template match="table" mode="label.markup"/>

    <!--
    *************************************************************
    Csak könyv szintű tartalomjegyzék generálása
    *************************************************************
    -->
    <xsl:param name="generate.toc" select="'book toc,title'"/>


</xsl:stylesheet>