package de.debuglevel.bragi.render.html

import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.places.Place
import de.debuglevel.bragi.render.DocumentRenderer
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class HtmlRenderer : DocumentRenderer {
    private val logger = KotlinLogging.logger {}

    override fun render(chapters: List<Chapter>, characters: List<Character>, places: List<Place>): Any {
        var output = ""

        output += renderHead()
        output += renderFrontMatter()
        output += renderChapters(chapters)
        output += renderCharacters(characters)
        output += renderPlaces(places)
        output += renderBackMatter()
        output += renderFoot()

        return output
    }

    // See: https://css4.pub/2015/oliver-twist/oliver-twist.html
    private fun renderFoot(): String {
        return """
            <script>

            function getText(e)
            {
                var text = "";

                for (var x = e.firstChild; x != null; x = x.nextSibling)
                {
            	if (x.nodeType == x.TEXT_NODE)
            	{
            	    text += x.data;
            	}
            	else if (x.nodeType == x.ELEMENT_NODE)
            	{
            	    text += getText(x);
            	}
                }

                return text;
            }

            function maketoc()
            {
                var hs = document.getElementsByTagName("h3");
                var toc = document.getElementById('toc-container');
                for(var i=0; i<hs.length; i++)
                {
            	var text = document.createTextNode(getText(hs[i]));
            	var span = document.createElement("span");
                    span.appendChild(text);
                    hs[i].setAttribute("id", "ch"+i);
            	var link = document.createElement("a");
            	link.setAttribute("href", "#ch"+i);
            	link.appendChild(span);
            	toc.appendChild(link); 
                }
            }

            </script>
            </body>
            </html>
        """.trimIndent()
    }

    private fun renderHead(): String {
        return """
            <html>
            <head>
            <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
            <title>Some book titlte by some author</title>
            <style>
            @import url(http://fonts.googleapis.com/css?family=Crimson+Text);
            
            @page {
              size: 130mm 198mm;
              margin: 20mm 14mm 16mm;
            }
            
            @page chapter {
              @bottom-center {
                font: 10pt 'Crimson Text', serif;
                content: counter(page);
                vertical-align: top;
                padding-top: 0.5em;
              }
            }
            
            html { 
              font: 10pt 'Crimson Text', serif;
              text-replace: "'" "\2019";
              hyphens: auto;
              counter-reset: chapter;
            }
            
            html, body, div, h1, h2, h3, h4, p, blockquote, ul, ol, li { 
              margin: 0; padding: 0;
            }
            
            h1, h2, h3, h4 { margin: 1em 0 0.6em; font-family: 'Crimson Text', serif; font-weight: normal; text-align: center; }
            h1 { font-size: 2em }
            h2 { font-size: 1.5em }
            h3 { font-size: 1.2em }
            h4 { font-size: 1em }
            
            a { color: black; text-decoration: none }
            
            blockquote { font-style: italic; margin: 1em }
            
            li { margin-left: 2em }
            
            p { text-align: justify }
            
            p + p { text-indent: 1.3em }
            
            hr {
              width: 3em;
              margin: 1em auto;
              border: none;
              border-top: thin solid black;
            }
            
            h1 { 
              font: 2em 'Crimson Text', Bergamo; 
              text-align: center;
            }
            
            section.titlepage, section.toc, section.chapter { page-break-before: always }
            
            /* titlepage */
            
            section.titlepage {
              padding-top: 3em;
              text-align: center;
            }
            
            /* toc */
            
            /* the table of contents (toc) is formatted as a table so that page
               numbers appears in a column of their own on the right side. 
            */
            
            #toc-container { display: table; width: 100% }
            
            .toc a {
              counter-increment: chapter-toc;
              display: table-row; 
              text-align: left;
            }
            
            .toc a span {
              padding-top: 1em;
              display: table-cell;
              page-break-inside: avoid;
            }
            
            .toc a span:after {
              content: leader(".");
            }
            
            .toc a:after {
              content: target-counter(attr(href), page);
              display: table-cell;
              text-align: right;
              vertical-align: bottom;
            }
            
            /* chapter */
            
            section.first.chapter {
              counter-reset: page 1;
            }
            
            section.chapter {
              page: chapter;
            }
            
            q { quotes: "\2018" "\2019" "\201C" "\201D" }
            
            /*
            h3:before { 
              display: block;
              content: "Chapter " counter(chapter, upper-roman);
              font-style: italic;
              font-size: 1.2em;
              text-align: center;
              margin: 0.3em 0;
            }*/
            
            h3 { 
              counter-increment: chapter;
              padding-left: 2em;
              text-indent: -1em;
            }
            
            section.colophon pre { font-size: 7pt }
            
            @media print {
              q:before { content: "\2018" }
              q:after { content: "\2019" }
              q q:before { content: "\201C" }
              q q:after { content: "\201D" }
            }
            
            @media screen {
              html { background: black; font-size: 20px }
              body { background: white; width: 30em; margin: 3em auto; padding: 2em; }
              section.toc, section.chapter { border-top: thin solid black; padding-top: 1.5em; margin-top: 1.5em }
            }
            
            /* additional bragi styles */
            .notes {
                background-color: yellow;
            }
            
            .summary {
                background-color: lightgray;
            }
            </style>
            </head>
            <body onload="maketoc();">            
        """.trimIndent()
    }

    private fun renderPlaces(places: List<Place>): String {
        logger.debug { "Rendering ${places.size} places..." }

        var output = "<h1>Places</h1>"
        output += places.joinToString("\n\n") {
            """
                <section class="chapter">
                    <h3>${it.name}</32>
                    <p class="aliases">${it.aliases.joinToString(", ")}</p>
                    <h4>Notes</h4>
                    <p class="notes" style="white-space: pre-wrap;">${it.notes}</p>
                </section>
                """.trimIndent()
        }

        return output
    }

    private fun renderCharacters(characters: List<Character>): String {
        logger.debug { "Rendering ${characters.size} characters..." }

        var output = "<h1>Characters</h1>"
        output += characters.joinToString("\n\n") {
            """
                <section class="chapter">
                    <h3>${it.name}</h3>
                    <p class="aliases">${it.aliases.joinToString(", ")}</p>
                    <h4>Notes</h4>
                    <p class="notes" style="white-space: pre-wrap;">${it.notes}</p>
                </section>
                """.trimIndent()
        }

        return output
    }

    private fun renderChapters(chapters: List<Chapter>): String {
        logger.debug { "Rendering ${chapters.size} chapters..." }

        var output = "<h1>Chapters</h1>"
        output += chapters.joinToString("\n\n") {
            """
                <section class="chapter">
                    <h3>${it.title}</h3>
                    <!--<h4>Content</h4>-->
                    <p class="content" style="white-space: pre-wrap;">${it.content}</p>
                    <h4>Summary</h4>
                    <p class="summary" style="white-space: pre-wrap;">${it.summary}</p>
                    <h4>Notes</h4>
                    <p class="notes" style="white-space: pre-wrap;">${it.notes}</p>
                </section>
                """.trimIndent()
        }

        return output
    }

    private fun renderBackMatter(): String {
        return ""
    }

    private fun renderFrontMatter(): String {
        return """
            <section class="titlepage">
                <h1>SOME BOOKTITLE</h1>
                <h4>OR</h4>
                <h2>THE AWKWARD SAMPLE TEXT</h2>
                <h4>BY</h4>
                <h2>SOME AUTHOR</h2>
            </section>
    
            <section class="toc">
                <h2>CONTENTS</h2>
                <div id="toc-container"></div> <!-- empty container to be filled in by JavaScript -->
            </section>
        """.trimIndent()
    }
}