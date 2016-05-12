<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2006/xpath-functions">
  <xsl:output method="html" encoding="UTF-8" indent="no" />

  <xsl:template match="/checkstyle">
    <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
    <html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
      <head>
				<meta http-equiv="cache-control" content="max-age=0" />
				<meta http-equiv="cache-control" content="no-cache" />
				<meta http-equiv="expires" content="0" />
				<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
				<meta http-equiv="pragma" content="no-cache" />

      	<link rel="stylesheet" type="text/css" href="css/checkstyle.css" />

      	<script type="text/javascript"
      		src="js/libs/jquery-2.2.3.min.js">
      	</script>

      	<script type="text/javascript"
      		src="js/checkstyle.js">
      	</script>
      </head>
      <body>
        <div class="num-errors">
          Checkstyle found <span class="error-count highlight-click"><xsl:value-of select="count(//error[@severity='error'])" /></span> error(s) and <span class="error-count highlight-click"><xsl:value-of select="count(//error[@severity='warning'])" /></span> warning(s) in <span class="file-count highlight-click"><xsl:value-of select="count(//file)" /></span> files.
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
        <div class="file-errors"><span class="file-name highlight-click"><xsl:value-of select="@name" /></span> (<span class="error-count highlight-click"><xsl:value-of select="count(error[@severity='error'])" /></span> error(s) and <span class="error-count highlight-click"><xsl:value-of select="count(error[@severity='warning'])" /></span> warning(s))</div>
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
        <span class="line highlight-click"><xsl:value-of select="@line" /></span>
      </td>
      <td class="column">
        <span class="column highlight-click"><xsl:value-of select="@column" /></span>
      </td>
      <td class="severity">
        <span class="severity highlight-click"><xsl:value-of select="@severity" /></span>
      </td>
      <td class="message">
        <span class="message highlight-click"><xsl:value-of select="@message" /></span>
      </td>
      <td class="source">
        <xsl:choose>
          <xsl:when test="contains(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), '.')">
            <span class="source highlight-click"><xsl:value-of select="substring-before(substring-after(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), '.'), 'Check')" /></span>
          </xsl:when>
          <xsl:otherwise>
            <span class="source highlight-click"><xsl:value-of select="substring-before(substring-after(@source, 'com.puppycrawl.tools.checkstyle.checks.'), 'Check')" /></span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
