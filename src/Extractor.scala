package tw.g0v

import org.apache.pdfbox.exceptions._
import org.apache.pdfbox.pdmodel._
import org.apache.pdfbox.pdmodel.common.PDStream
import org.apache.pdfbox.util._

import scala.collection.JavaConverters._

object Extractor extends App {
	val doc = PDDocument.load("test.pdf")
	val printer = new PrintTextLocations()

	val pages: List[PDPage] = doc.getDocumentCatalog().getAllPages().asScala.toList.asInstanceOf[List[PDPage]]
	pages.foreach((page: PDPage) => {
		val contents = page.getContents
		if (contents != null) {
			printer.processStream(page, page.findResources, page.getContents.getStream)
		}
	})
}

class PrintTextLocations extends PDFTextStripper {
	override def processTextPosition(text: TextPosition) =
	{
        System.out.println( "String[" + text.getXDirAdj() + "," +
                text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
                text.getXScale() + " height=" + text.getHeightDir() + " space=" +
                text.getWidthOfSpace() + " width=" +
                text.getWidthDirAdj() + "]" + text.getCharacter() );
    }
}