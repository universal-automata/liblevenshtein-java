<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2006/xpath-functions">
  <xsl:output method="html" encoding="UTF-8" indent="no" />

  <xsl:template match="/checkstyle">
    <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
    <html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
      <head>
        <style>
          ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
          }
          table {
            width: 100%;
            margin-top: 0.2em;
            border: 0.1em solid #888;
            border-right: none;
          }
          th.line {
            width: 5%;
          }
          th.column {
            width: 5%;
          }
          th.severity {
            width: 5%;
          }
          th.message {
            width: 70%;
          }
          th.source {
            width: 15%;
          }
          th {
            background-color: #A0A0A0;
            color: #F8F8F8;
          }
          tbody tr:nth-child(odd) {
            background-color: #F8F8F8;
          }
          tbody tr:nth-child(even) {
            background-color: #D8D8D8;
          }
          td, th {
            border-bottom: 0.1em solid #888;
            border-right: 0.1em solid #888;
            padding-left: 0.2em;
            padding-right: 0.2em;
          }
          tbody tr:last-child td {
            border-bottom: none;
          }
          div.file-errors {
            font-family: monospace;
            font-size: large;
            font-weight: normal;
            padding-top: 1em;
            padding-bottom: 1em;
            background-color: #EEE;
            color: #900;
            border: 0.1em solid #888;
            text-align: center;
          }
          li.file {
            margin-top: 1em;
            margin-bottom: 1em;
          }
          td.message {
            font-style: italic;
          }
          div.num-errors {
            margin-top: 0.2em;
            margin-bottom: 0.2em;
            padding-top: 0.5em;
            padding-bottom: 0.5em;
            font-size: 1.5em;
            font-weight: bold;
            font-style: italic;
            background-color: #C0C0C0;
            border: 0.1em solid #888;
            text-align: center;
            color: #900;
          }
          div.file-name {
            display: inline-block;
          }
        </style>
      </head>
      <body>
        <div class="num-errors">
          Checkstyle found <span class="error-count"><xsl:value-of select="count(//error)" /></span> errors in <span class="file-count"><xsl:value-of select="count(//file)" /></span> files.
        </div>
        <ul class="files">
          <xsl:apply-templates select="file" />
        </ul>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="file">
    <xsl:if test="error"><!-- Only render if there are errors -->
      <li class="file">
        <div class="file-errors"><div class="file-name"><xsl:value-of select="@name" /></div> (<span class="error-count"><xsl:value-of select="count(error)" /></span> errors)</div>
        <table class="errors" border="0" cellspacing="0">
          <thead>
            <tr class="error">
              <th class="line">
                Line
              </th>
              <th class="column">
                Column
              </th>
              <th class="severity">
                Severity
              </th>
              <th class="message">
                Message
              </th>
              <th class="source">
                Source
              </th>
            </tr>
          </thead>
          <tbody>
            <xsl:apply-templates select="error">
              <!-- Render the errors in reverse so if they require a code change
              the file lines for subsequent errors won't require shifting. -->
              <xsl:sort select="position()" data-type="number" order="descending" />
            </xsl:apply-templates>
          </tbody>
        </table>
      </li>
    </xsl:if>
  </xsl:template>

  <xsl:template match="error">
    <tr class="error">
      <td class="line">
        <xsl:value-of select="@line" />
      </td>
      <td class="column">
        <xsl:value-of select="@column" />
      </td>
      <td class="severity">
        <xsl:value-of select="@severity" />
      </td>
      <td class="message">
        <xsl:value-of select="@message" />
      </td>
      <td class="source">
        <xsl:choose>
          <xsl:when test="contains(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), '.')">
            <xsl:value-of select="substring-before(substring-after(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), '.'), 'Check')" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="substring-before(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), 'Check')" />
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
