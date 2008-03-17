/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2006-2007, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

// $Id: StringReader.scala 12268 2007-07-11 13:45:53Z michelou $

package scala.util.parsing.input

/** An object encapsulating basic character constants
 *
 * @author Martin Odersky, Adriaan Moors
 */
object CharSequenceReader {
  final val EofCh = '\032'
}

/** A character array reader reads a stream of characters (keeping track of their positions)
 * from an array.
 *
 * @param source the source sequence
 * @param offset  starting offset.
 *
 * @author Martin Odersky
 */
class CharSequenceReader(override val source: CharSequence,
                         override val offset: Int) extends Reader[Char] {
  import CharSequenceReader._

  /** Construct a <code>CharSequenceReader</code> with its first element at
   *  <code>source(0)</code> and position <code>(1,1)</code>.
   */
  def this(source: CharSequence) = this(source, 0)

  /** Returns the first element of the reader, or EofCh if reader is at its end
   */
  def first =
    if (source.isDefinedAt(offset)) source(offset) else EofCh

  /** Returns a CharSequenceReader consisting of all elements except the first
   *
   * @return If <code>atEnd</code> is <code>true</code>, the result will be
   *         <code>this'; otherwise, it's a <code>CharSequenceReader</code> containing
   *         the rest of input.
   */
  def rest: CharSequenceReader =
    if (source.isDefinedAt(offset)) new CharSequenceReader(source, offset + 1)
    else this

  /** The position of the first element in the reader
   */
  def pos: Position = new OffsetPosition(source, offset)

  /** true iff there are no more elements in this reader (except for trailing
   *  EofCh's)
   */
  def atEnd = !source.isDefinedAt(offset)

  /** Returns an abstract reader consisting of all elements except the first
   *  <code>n</code> elements.
   */
  override def drop(n: Int): CharSequenceReader =
    new CharSequenceReader(source, offset + n)
}
