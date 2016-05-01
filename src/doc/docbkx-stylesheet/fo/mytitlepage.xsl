<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:date="http://exslt.org/dates-and-times"
                version="1.0">

    <xsl:template name="book.titlepage.recto">
        <fo:block-container>
            <fo:block font-size="18pt" text-align="center">
                <xsl:value-of select="d:title"/>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="12cm" position="absolute">
            <fo:block font-size="12pt" text-align="right">
                <xsl:value-of select="d:subtitle"/>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="21cm" position="absolute">
            <fo:block text-align="center">
                <xsl:text>Debrecen</xsl:text>
            </fo:block>
            <fo:block text-align="center">
                <xsl:call-template name="datetime.format">
                    <xsl:with-param name="date" select="date:date-time()"/>
                    <xsl:with-param name="format" select="'Y'"/>
                </xsl:call-template>
            </fo:block>
        </fo:block-container>
    </xsl:template>

    <xsl:template name="book.titlepage.verso">
        <fo:block-container>
            <fo:block font-size="12pt" text-align="center">
                <xsl:text>Debreceni Egyetem</xsl:text>
            </fo:block>
            <fo:block font-size="12pt" text-align="center">
                <xsl:text>Informatikai kar</xsl:text>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="10cm" position="absolute">
            <fo:block font-size="16pt" text-align="center">
                <xsl:text>Snapszer kártyajáték fejlesztése</xsl:text>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="16cm" left="0cm" position="absolute">
            <fo:block font-size="12pt" text-align="left">
                <xsl:text>Témavezető: Dr. Halász Gábor József</xsl:text>
            </fo:block>
            <fo:block font-size="12pt" text-align="left">
                <xsl:text>Egyetemi docens, dékánhelyettes</xsl:text>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="16cm" left="10cm" position="absolute">
            <fo:block font-size="12pt" text-align="left">
                <xsl:text>Készítette: Sipos Ferenc</xsl:text>
            </fo:block>

            <fo:block font-size="12pt" text-align="left">
                <xsl:text>Programtervező informatikus</xsl:text>
            </fo:block>
        </fo:block-container>
        <fo:block-container top="21cm" position="absolute">
            <fo:block text-align="center">
                <xsl:text>Debrecen</xsl:text>
            </fo:block>
            <fo:block text-align="center">
                <xsl:call-template name="datetime.format">
                    <xsl:with-param name="date" select="date:date-time()"/>
                    <xsl:with-param name="format" select="'Y'"/>
                </xsl:call-template>
            </fo:block>
        </fo:block-container>
    </xsl:template>

    <xsl:template name="book.titlepage.separator"/>


</xsl:stylesheet>