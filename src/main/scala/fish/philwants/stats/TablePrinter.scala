package fish.philwants.stats

object TablePrinter {
  def format(table: Seq[Seq[Any]]): String = {
    // Replace each cell String with the length of content
    val cell_lengths: Seq[Seq[Int]] = table.map { row =>
      row.map { cell =>
        if (cell == null) 0
        else cell.toString.size
      }
    }

    // Find the length of the largest content for each column
    val max_column_sizes = cell_lengths.transpose.map { c => c.max }

    // Get the String for each row
    val rows = table.map { r => formatRow(r, max_column_sizes)}

    // Get the String for the full table
    formatRows(rowSeparator(max_column_sizes), rows)
  }

  /**
    * Adjusts the content for each cell in a row to the columns width. A pipe is used as a cell separator.
    * @param row A row of cells
    * @param colSizes The width for each column
    * @return The adjusted width content and separators
    */
  def formatRow(row: Seq[Any], colSizes: Seq[Int]) = {
    // Get the fixed-width content for each cell
    val cells = row.zip(colSizes).map { case (content, size) =>
      if (size == 0) ""
      else ("%" + size + "s").format(content)
    }
    cells.mkString("|", "|", "|")
  }

  /**
    * Create the the full table with separator rows between the data and hte headers.
    * @param rowSeparator
    * @param rows
    * @return
    */
  def formatRows(rowSeparator: String, rows: Seq[String]): String = {
    val formattedRows = Seq(
      rowSeparator,
      rows.head,
      rowSeparator,
      rows.tail.mkString("\n"),
      rowSeparator)
    formattedRows.mkString("\n")
  }


  /**
    * Create a row separator. Where '-' is used to create lines and '+' is used for junction points
    * @param colSizes The width of each column
    * @return A string to separate each row
    */
  def rowSeparator(colSizes: Seq[Int]) = colSizes.map { i => "-" * i }.mkString("+", "+", "+")
}
