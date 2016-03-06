<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <!-- imports the original docbook stylesheet -->
    <xsl:import href="urn:docbkx:stylesheet"/>

    <!-- set bellow all your custom xsl configuration -->

    <xsl:param name="chapter.autolabel">0</xsl:param>
    <xsl:param name="local.l10n.xml" select="document('')"/>

    <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
        <l:l10n language="en">
            <l:context name="title-numbered">
                <l:template name="chapter" text="%n.&#160;%t"/>
            </l:context>
        </l:l10n>
    </l:i18n>

    <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
        <l:l10n language="en">
            <l:gentext key="TableofContents" text="Tartalomjegyzék"/>
        </l:l10n>
    </l:i18n>


    <xsl:template name="initial.page.number">
        <xsl:param name="element" select="local-name(.)"/>
        <xsl:param name="master-reference" select="''"/>

        <xsl:choose>
            <xsl:when test="$element = 'toc'">1</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="page.number.format">
        <xsl:param name="element" select="local-name(.)"/>
        <xsl:param name="master-reference" select="''"/>

        <xsl:choose>
            <xsl:when test="$element = 'toc'">1</xsl:when>
            <xsl:when test="$element = 'chapter'">1</xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- clear verso -->
    <xsl:template name="book.titlepage.verso"/>
    <!-- clear page break after verso -->
    <xsl:template name="book.titlepage.before.verso"/>


</xsl:stylesheet>