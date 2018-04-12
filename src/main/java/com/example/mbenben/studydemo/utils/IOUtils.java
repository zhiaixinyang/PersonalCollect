package com.example.mbenben.studydemo.utils;

import com.suapp.common.io.output.ByteArrayOutputStream;
import com.suapp.common.io.output.StringBuilderWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class to handle I/O operations.
 * some method copy from org.apache.commons.io.IOUtils
 */
public class IOUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 文件或流的结束符
     */
    public static final int EOF = -1;

    /**
     * The Unix directory separator character.
     * Unix 系统的目录分割符
     */
    public static final char DIR_SEPARATOR_UNIX = '/';
    /**
     * The Windows directory separator character.
     * Windows系统的目录分隔符
     */
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    /**
     * The system directory separator character.
     */
    public static final char DIR_SEPARATOR = File.separatorChar;
    /**
     * The Unix line separator string.
     */
    public static final String LINE_SEPARATOR_UNIX = "\n";
    /**
     * The Windows line separator string.
     */
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    /**
     * 行分隔符，从系统中读取
     */
    public static final String LINE_SEPARATOR;


    static {
        // avoid security issues
        try (final StringBuilderWriter buf = new StringBuilderWriter(4);
             final PrintWriter out = new PrintWriter(buf)) {
            out.println();
            LINE_SEPARATOR = buf.toString();
        }
    }

    /**
     * The default buffer size ({@value}) to use for
     * {@link #copyLarge(InputStream, OutputStream)}
     * and
     * {@link #copyLarge(Reader, Writer)}
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    /**
     * The default buffer size to use for the skip() methods.
     */
    private static final int SKIP_BUFFER_SIZE = 2048;

    private static char[] SKIP_CHAR_BUFFER;
    private static byte[] SKIP_BYTE_BUFFER;


    private IOUtils() {
    }

    /**
     * Read the Stream content as a string (use utf-8).
     *
     * @param is The stream to read
     * @return The String content
     * @throws IOException IOException
     */
    public static String readString(InputStream is) throws IOException {
        return readString(is, DEFAULT_ENCODING);
    }

    /**
     * Read the Stream content as a string.
     *
     * @param is The stream to read
     * @return The String content
     * @throws IOException IOException
     */
    public static String readString(InputStream is, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        try {
            copy(is, sw, encoding);
            return sw.toString();
        } finally {
            close(is);
            close(sw);
        }
    }

    /**
     * 将 char[]数组转成 bytes[]数组
     * <p>
     * 注意:默认是 {@link #DEFAULT_ENCODING}编码格式
     *
     * @param chars
     * @return byte[]
     */
    private static byte[] getBytes(char[] chars) {
        Charset cs = Charsets.toCharset(DEFAULT_ENCODING);
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    /**
     * Read file content to a String
     * <p>
     * 注意:默认是 {@link #DEFAULT_ENCODING}编码格式
     *
     * @param file The file to read
     * @return The String content
     * @throws IOException IOException
     */
    public static String readString(File file) throws IOException {
        return readString(file, DEFAULT_ENCODING);
    }

    /**
     * Read file content to a String.
     *
     * @param file The file to read
     * @return The String content
     * @throws IOException IOException
     */
    public static String readString(File file, String encoding) throws IOException {
        return readString(new FileInputStream(file), encoding);
    }

    /**
     * 将文件读成bytes 数组
     * <p>
     * <b>注意: 不要用于大文件 !</b>
     *
     * @param file The file te read
     * @return The binary data
     * @throws IOException IOException
     */
    public static byte[] readBytes(File file) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] result = new byte[(int) file.length()];
            is.read(result);
            return result;
        } finally {
            close(is);
        }
    }

    /**
     * 将流读成 bytes[]数组
     * <p>
     * <b>注意: 不要用于流比较大的情况，否则会内存溢出 !</b>
     * </p>
     *
     * @param is The stream to read
     * @return The binary data
     * @throws IOException IOException
     */
    public static byte[] readBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int read;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = is.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            return baos.toByteArray();
        } finally {
            close(is);
            close(baos);
        }
    }

    /**
     * 将 String 串 写入到指定的输出流中
     * <p>
     * 注意:默认是 {@link #DEFAULT_ENCODING}编码格式
     *
     * @param content The content to read
     * @param os      The stream to write
     * @throws IOException IOException
     */
    public static void writeString(String content, OutputStream os) throws IOException {
        writeString(content, os, DEFAULT_ENCODING);
    }

    /**
     * 将 String 串 写入到指定的输出流中，使用指定的编码格式
     *
     * @param content The content to read
     * @param os      The stream to write
     * @throws IOException IOException
     */
    public static void writeString(String content, OutputStream os, String encoding)
            throws IOException {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(os, encoding));
            printWriter.write(content);
            printWriter.flush();
            os.flush();
        } finally {
            close(os);
        }
    }

    /**
     * 将字符串内容写入到给定的文件中，
     * <p>
     * <b>注意: 覆盖读写 !</b>
     * </p>
     *
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeString(String content, File file) throws IOException {
        writeString(content, new FileOutputStream(file));
    }

    /**
     * Close stream.
     *
     * @param is The stream to close
     */
    public static void close(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Closes a URLConnection.
     *
     * @param conn the connection to close.
     */
    public static void close(final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    // from org.apache.commons.io.IOUtils

    /**
     * 返回一个包装的带缓冲的 <code>InputStream</code>，相当于 <code>BufferedInputStream</code>，
     * 使用 返回的带缓冲的 <code>InputStream</code> 可以避免不必要的 <code>bytes[]</code>数组分配,
     * 所以，没必要再使用 <code>BufferedInputStream</code>.
     *
     * @param input Stream to be fully buffered.
     * @return A fully buffered stream.
     * @throws IOException if an I/O error occurs
     */
    public static InputStream toBufferedInputStream(final InputStream input) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input);
    }

    /**
     * 返回一个包装的带缓冲的 <code>InputStream</code>，相当于 <code>BufferedInputStream</code>，
     * 使用 返回的带缓冲的 <code>InputStream</code> 可以避免不必要的 <code>bytes[]</code>数组分配,
     * 所以，没必要必要在使用 <code>BufferedInputStream</code>.
     * <p>
     * 和{@link #toBufferedInputStream(InputStream)}相比 可以自定义缓冲大小，默认是 1024KB
     *
     * @param input Stream to be fully buffered.
     * @param size  the initial buffer size
     * @return A fully buffered stream.
     * @throws IOException if an I/O error occurs
     */
    public static InputStream toBufferedInputStream(final InputStream input, final int size) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input, size);
    }

    /**
     * 将 Reader 包装成 带Buffer的 Reader，如果 Reader 本身就是 BufferedReader 了，直接返回
     * <p>
     * 同{@link #buffer(Reader)}
     *
     * @param reader the reader to wrap or return (not null)
     * @return the given reader or a new {@link BufferedReader} for the given reader
     * @throws NullPointerException if the input parameter is null
     * @see #buffer(Reader)
     */
    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 将 Reader 包装成 带Buffer的 Reader，如果 Reader 本身就是 BufferedReader 了，直接返回
     * <p>
     * 同{@link #buffer(Reader)}
     *
     * @param reader the reader to wrap or return (not null)
     * @param size   the buffer size, if a new BufferedReader is created.
     * @return the given reader or a new {@link BufferedReader} for the given reader
     * @throws NullPointerException if the input parameter is null
     * @see #buffer(Reader)
     */
    public static BufferedReader toBufferedReader(final Reader reader, final int size) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, size);
    }

    /**
     * 将 Reader 包装成 带Buffer的 Reader，如果 Reader 本身就是 BufferedReader 了，直接返回
     * <p>
     * 同{@link #toBufferedReader(Reader)}}
     *
     * @param reader the reader to wrap or return (not null)
     * @return the given reader or a new {@link BufferedReader} for the given reader
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedReader buffer(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 将 Reader 包装成 带Buffer的 Reader，如果 Reader 本身就是 BufferedReader 了，直接返回
     * <p>
     * 同{@link #toBufferedReader(Reader, int)}
     *
     * @param reader the reader to wrap or return (not null)
     * @param size   the buffer size, if a new BufferedReader is created.
     * @return the given reader or a new {@link BufferedReader} for the given reader
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedReader buffer(final Reader reader, final int size) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, size);
    }

    /**
     * 将 Writer 包装成 带Buffer的 Writer，如果 Writer 本身就是 BufferedWriter 了，直接返回
     *
     * @param writer the Writer to wrap or return (not null)
     * @return the given Writer or a new {@link BufferedWriter} for the given Writer
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedWriter buffer(final Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    /**
     * 将 Writer 包装成 带Buffer的 Writer，如果 Writer 本身就是 BufferedWriter 了，直接返回
     *
     * @param writer the Writer to wrap or return (not null)
     * @param size   the buffer size, if a new BufferedWriter is created.
     * @return the given Writer or a new {@link BufferedWriter} for the given Writer
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedWriter buffer(final Writer writer, final int size) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, size);
    }

    /**
     * 如果它已经是{@link BufferedOutputStream}，则返回给定的 OutputStream，否则从给定的OutputStream中创建一个BufferedOutputStream
     *
     * @param outputStream the OutputStream to wrap or return (not null)
     * @return the given OutputStream or a new {@link BufferedOutputStream} for the given OutputStream
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedOutputStream buffer(final OutputStream outputStream) {
        // reject null early on rather than waiting for IO operation to fail
        if (outputStream == null) { // not checked by BufferedOutputStream
            throw new NullPointerException();
        }
        return outputStream instanceof BufferedOutputStream ?
                (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    /**
     * 如果它已经是{@link BufferedOutputStream}，则返回给定的 OutputStream，否则从给定的 OutputStream 中创建一个 BufferedOutputStream
     *
     * @param outputStream the OutputStream to wrap or return (not null)
     * @param size         the buffer size, if a new BufferedOutputStream is created.
     * @return the given OutputStream or a new {@link BufferedOutputStream} for the given OutputStream
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedOutputStream buffer(final OutputStream outputStream, final int size) {
        // reject null early on rather than waiting for IO operation to fail
        if (outputStream == null) { // not checked by BufferedOutputStream
            throw new NullPointerException();
        }
        return outputStream instanceof BufferedOutputStream ?
                (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, size);
    }

    /**
     * 返回一个带 Buffer 的 包装 BufferedInputStream,如果它本身就是一个 {@link BufferedInputStream} 直接返回
     *
     * @param inputStream the InputStream to wrap or return (not null)
     * @return the given InputStream or a new {@link BufferedInputStream} for the given InputStream
     * @throws NullPointerException if the input parameter is null
     */
    public static BufferedInputStream buffer(final InputStream inputStream) {
        // reject null early on rather than waiting for IO operation to fail
        if (inputStream == null) { // not checked by BufferedInputStream
            throw new NullPointerException();
        }
        return inputStream instanceof BufferedInputStream ?
                (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    /**
     * 返回一个带 Buffer 的 包装 BufferedInputStream,如果它本身就是一个 {@link BufferedInputStream} 直接返回
     *
     * @param inputStream the InputStream to wrap or return (not null)
     * @param size        the buffer size, if a new BufferedInputStream is created.
     * @return the given InputStream or a new {@link BufferedInputStream} for the given InputStream
     * @throws NullPointerException if the input parameter is null
     * @since 2.5
     */
    public static BufferedInputStream buffer(final InputStream inputStream, final int size) {
        // reject null early on rather than waiting for IO operation to fail
        if (inputStream == null) { // not checked by BufferedInputStream
            throw new NullPointerException();
        }
        return inputStream instanceof BufferedInputStream ?
                (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, size);
    }

    // read toByteArray
    //-----------------------------------------------------------------------

    /**
     * 获取 <code>InputStream</code> 中的字节码内容，此方法在内部使用了带缓冲的 <code>BufferedInputStream</code>
     * 所以不再需要将 参数 InputStream 包装成 <code>BufferedInputStream</code>.
     *
     * @param input the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(input, output);
            return output.toByteArray();
        }
    }

    /**
     * 获取 <code>InputStream</code> 中的字节码内容.
     * 当知道 <code>InputStream</code> 的长度时，我们使用该方法替代{@link #toByteArray(InputStream)},
     * <p>
     * <b>注意:</b> 该方法在 执行前就会检查 size的大小，防止 size 大于 <code>Integer.MAX_VALUE</code>
     *
     * @param input the <code>InputStream</code> to read from
     * @param size  the size of <code>InputStream</code>
     * @return the requested byte array
     * @throws IOException              if an I/O error occurs or <code>InputStream</code> size differ from parameter
     *                                  size
     * @throws IllegalArgumentException if size is less than zero or size is greater than Integer.MAX_VALUE
     * @see IOUtils#toByteArray(InputStream, int)
     */
    public static byte[] toByteArray(final InputStream input, final long size) throws IOException {

        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        }

        return toByteArray(input, (int) size);
    }

    /**
     * 获取 <code>InputStream</code> 中的字节码内容.
     * 当知道 <code>InputStream</code> 的长度时，我们使用该方法替代{@link #toByteArray(InputStream)}.
     * 该方法会被{@link #toByteArray(InputStream, long)}调用
     *
     * @param input the <code>InputStream</code> to read from
     * @param size  the size of <code>InputStream</code>
     * @return the requested byte array
     * @throws IOException              if an I/O error occurs or <code>InputStream</code> size differ from parameter
     *                                  size
     * @throws IllegalArgumentException if size is less than zero
     */
    public static byte[] toByteArray(final InputStream input, final int size) throws IOException {

        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }

        if (size == 0) {
            return new byte[0];
        }

        final byte[] data = new byte[size];
        int offset = 0;
        int read;

        while (offset < size && (read = input.read(data, offset, size - offset)) != EOF) {
            offset += read;
        }

        if (offset != size) {
            throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
        }

        return data;
    }

    /**
     * 获取 <code>Reader</code> 中的字节码内容.
     * <p>
     * <b>注意:<b/> 默认是 {@link #DEFAULT_ENCODING}编码格式
     *
     * @param input the <code>Reader</code> to read from
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static byte[] toByteArray(final Reader input) throws IOException {
        return toByteArray(input, Charset.defaultCharset());
    }

    /**
     * 获取 <code>Reader</code> 中的字节码内容.使用给定的编码格式
     *
     * @param input    the <code>Reader</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static byte[] toByteArray(final Reader input, final Charset encoding) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(input, output, encoding);
            return output.toByteArray();
        }
    }

    /**
     * 获取 <code>Reader</code> 中的字节码内容.使用给定的编码格式.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     *
     * @param input    the <code>Reader</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested byte array
     * @throws NullPointerException                         if the input is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static byte[] toByteArray(final Reader input, final String encoding) throws IOException {
        return toByteArray(input, Charsets.toCharset(encoding));
    }

    /**
     * 该方法和{@link String#getBytes()}相同.
     *
     * @param input the <code>String</code> to convert
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs (never occurs)
     * @see String#getBytes()
     */
    public static byte[] toByteArray(final String input) throws IOException {
        return input.getBytes(Charset.defaultCharset());
    }

    /**
     * 获取<code>URI</code>  中的byte数组,
     * <p>
     * 可能需要联网
     *
     * @param uri the <code>URI</code> to read
     * @return the requested byte array
     * @throws NullPointerException if the uri is null
     * @throws IOException          if an I/O exception occurs
     */
    public static byte[] toByteArray(final URI uri) throws IOException {
        return IOUtils.toByteArray(uri.toURL());
    }

    /**
     * 获取<code>URL</code>  中的byte数组,
     * <p>
     * 可能需要联网
     *
     * @param url the <code>URL</code> to read
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O exception occurs
     */
    public static byte[] toByteArray(final URL url) throws IOException {
        final URLConnection conn = url.openConnection();
        try {
            return IOUtils.toByteArray(conn);
        } finally {
            close(conn);
        }
    }

    /**
     * 获取<code>URLConnection</code>  中的byte数组,
     * <p>
     * 可能需要联网
     *
     * @param urlConn the <code>URLConnection</code> to read
     * @return the requested byte array
     * @throws NullPointerException if the urlConn is null
     * @throws IOException          if an I/O exception occurs
     */
    public static byte[] toByteArray(final URLConnection urlConn) throws IOException {
        try (InputStream inputStream = urlConn.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    // read char[]
    //-----------------------------------------------------------------------

    /**
     * 获取 <code>InputStream</code> 中的内容，并转成 character 数组,
     * <p>
     * <b>注意:<b/> 使用系统默认编码格式
     *
     * @param is the <code>InputStream</code> to read from
     * @return the requested character array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static char[] toCharArray(final InputStream is) throws IOException {
        return toCharArray(is, Charset.defaultCharset());
    }

    /**
     * 获取 <code>InputStream</code> 中的内容，并转成 character 数组
     *
     * @param is       the <code>InputStream</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested character array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static char[] toCharArray(final InputStream is, final Charset encoding)
            throws IOException {
        final CharArrayWriter output = new CharArrayWriter();
        copy(is, output, encoding);
        return output.toCharArray();
    }

    /**
     * 获取 <code>InputStream</code> 中的内容，并转成 character 数组
     *
     * @param is       the <code>InputStream</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested character array
     * @throws NullPointerException                         if the input is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static char[] toCharArray(final InputStream is, final String encoding) throws IOException {
        return toCharArray(is, Charsets.toCharset(encoding));
    }

    /**
     * 获取 <code>Reader</code> 中的内容，并转成 character 数组
     *
     * @param input the <code>Reader</code> to read from
     * @return the requested character array
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static char[] toCharArray(final Reader input) throws IOException {
        final CharArrayWriter sw = new CharArrayWriter();
        copy(input, sw);
        return sw.toCharArray();
    }

    // read toString
    //-----------------------------------------------------------------------

    /**
     * 获取 <code>InputStream</code> 中的内容并转成 String 串.
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param input the <code>InputStream</code> to read from
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     * @see #toString(InputStream, Charset)
     */
    public static String toString(final InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    /**
     * 获取 <code>InputStream</code> 中的内容并转成 String 串.
     *
     * @param input    the <code>InputStream</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static String toString(final InputStream input, final Charset encoding) throws IOException {
        try (final StringBuilderWriter sw = new StringBuilderWriter()) {
            copy(input, sw, encoding);
            return sw.toString();
        }
    }

    /**
     * 获取 <code>InputStream</code> 中的内容并转成 String 串.
     *
     * @param input    the <code>InputStream</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested String
     * @throws NullPointerException                         if the input is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static String toString(final InputStream input, final String encoding)
            throws IOException {
        return toString(input, Charsets.toCharset(encoding));
    }

    /**
     * 获取 <code>Reader</code> 中的字符串内容
     *
     * @param input the <code>Reader</code> to read from
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static String toString(final Reader input) throws IOException {
        try (final StringBuilderWriter sw = new StringBuilderWriter()) {
            copy(input, sw);
            return sw.toString();
        }
    }

    /**
     * Gets the contents at the given URI.
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param uri The URI source.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     */
    public static String toString(final URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }

    /**
     * Gets the contents at the given URI.
     *
     * @param uri      The URI source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     */
    public static String toString(final URI uri, final Charset encoding) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(encoding));
    }

    /**
     * Gets the contents at the given URI.
     *
     * @param uri      The URI source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static String toString(final URI uri, final String encoding) throws IOException {
        return toString(uri, Charsets.toCharset(encoding));
    }

    /**
     * Gets the contents at the given URL.
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param url The URL source.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     */
    public static String toString(final URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }

    /**
     * Gets the contents at the given URL.
     *
     * @param url      The URL source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     */
    public static String toString(final URL url, final Charset encoding) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            return toString(inputStream, encoding);
        }
    }

    /**
     * Gets the contents at the given URL.
     *
     * @param url      The URL source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static String toString(final URL url, final String encoding) throws IOException {
        return toString(url, Charsets.toCharset(encoding));
    }

    /**
     * 该方法同 {@link String#String(byte[])}
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param input the byte array to read from
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs (never occurs)
     * @see String#String(byte[])
     */
    public static String toString(final byte[] input) throws IOException {
        // make explicit the use of the default charset
        return new String(input, Charset.defaultCharset());
    }

    /**
     * Gets the contents of a <code>byte[]</code> as a String
     * using the specified character encoding.
     *
     * @param input    the byte array to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs (never occurs)
     */
    public static String toString(final byte[] input, final String encoding) throws IOException {
        return new String(input, Charsets.toCharset(encoding));
    }

    // readLines
    //-----------------------------------------------------------------------

    /**
     * 获取  <code>InputStream</code> 中的内容，并按行读字符串，返回一个 按行存储的List列表
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input the <code>InputStream</code> to read from, not null
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     * @see #readLines(InputStream, Charset)
     */
    public static List<String> readLines(final InputStream input) throws IOException {
        return readLines(input, Charset.defaultCharset());
    }

    /**
     * 获取  <code>InputStream</code> 中的内容，并按行读字符串，返回一个 按行存储的List列表
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static List<String> readLines(final InputStream input, final Charset encoding) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(encoding));
        return readLines(reader);
    }

    /**
     * 获取  <code>InputStream</code> 中的内容，并按行读字符串，返回一个 按行存储的List列表
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return the list of Strings, never null
     * @throws NullPointerException                         if the input is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static List<String> readLines(final InputStream input, final String encoding) throws IOException {
        return readLines(input, Charsets.toCharset(encoding));
    }

    /**
     * 获取  <code>Reader</code> 中的内容，并按行读字符串，返回一个 按行存储的List列表
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input the <code>Reader</code> to read from, not null
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static List<String> readLines(final Reader input) throws IOException {
        final BufferedReader reader = toBufferedReader(input);
        final List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    /**
     * 获取  <code>Reader</code> 中的第一行内容,
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input the <code>Reader</code> to read from, not null
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static String readLine(Reader input) throws IOException {
        final BufferedReader reader = toBufferedReader(input);
        final List<String> list = new ArrayList<>();
        String line = reader.readLine();
        return line;
    }

    /**
     * 获取  <code>InputStream</code> 中的第一行内容
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return the list of Strings, never null
     * @throws NullPointerException                         if the input is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static String readLine(InputStream input, String encoding) throws IOException {
        return readLine(input, Charsets.toCharset(encoding));
    }

    /**
     * 获取  <code>InputStream</code> 中的第一行内容
     * <strong>注意:读取完后没有主动关闭输入，请自行关闭</strong>
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static String readLine(InputStream input, Charset encoding) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, encoding);
        return readLine(reader);
    }

    // lineIterator
    //-----------------------------------------------------------------------

    /**
     * 返回一个行迭代器，很方便对 <code>Reader</code> 一行行的做处理。
     * <p>
     * <p>
     * 在使用完后记得要调用 {@link LineIterator#close()} or {@link LineIterator#closeQuietly(LineIterator)}
     * 来关闭
     * <p>
     * 推荐用法:
     * <pre>
     * try {
     *   LineIterator it = IOUtils.lineIterator(reader);
     *   while (it.hasNext()) {
     *     String line = it.nextLine();
     *     /// do something with line
     *   }
     * } finally {
     *   IOUtils.closeQuietly(reader);
     * }
     * </pre>
     *
     * @param reader the <code>Reader</code> to read from, not null
     * @return an Iterator of the lines in the reader, never null
     * @throws IllegalArgumentException if the reader is null
     */
    public static LineIterator lineIterator(final Reader reader) {
        return new LineIterator(reader);
    }

    /**
     * 返回一个行迭代器，很方便对 <code>InputStream</code> 一行行的做处理。
     * <p>
     * 在使用完后记得要调用 {@link LineIterator#close()} or {@link LineIterator#closeQuietly(LineIterator)}
     * 来关闭
     * 推荐用法:
     * <pre>
     * try {
     *   LineIterator it = IOUtils.lineIterator(stream, charset);
     *   while (it.hasNext()) {
     *     String line = it.nextLine();
     *     /// do something with line
     *   }
     * } finally {
     *   IOUtils.closeQuietly(stream);
     * }
     * </pre>
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return an Iterator of the lines in the reader, never null
     * @throws IllegalArgumentException if the input is null
     * @throws IOException              if an I/O error occurs, such as if the encoding is invalid
     */
    public static LineIterator lineIterator(final InputStream input, final Charset encoding) throws IOException {
        return new LineIterator(new InputStreamReader(input, Charsets.toCharset(encoding)));
    }

    /**
     * 返回一个行迭代器，很方便对 <code>InputStream</code> 一行行的做处理。
     * <p>
     * 在使用完后记得要调用 {@link LineIterator#close()} or {@link LineIterator#closeQuietly(LineIterator)}
     * 来关闭
     *
     * @param input    the <code>InputStream</code> to read from, not null
     * @param encoding the encoding to use, null means platform default
     * @return an Iterator of the lines in the reader, never null
     * @throws IllegalArgumentException                     if the input is null
     * @throws IOException                                  if an I/O error occurs, such as if the encoding is invalid
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     * @see #lineIterator(InputStream, Charset)
     */
    public static LineIterator lineIterator(final InputStream input, final String encoding) throws IOException {
        return lineIterator(input, Charsets.toCharset(encoding));
    }
    // to InputStream
    //-----------------------------------------------------------------------

    /**
     * 将 字符串转成流
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param input the CharSequence to convert
     * @return an input stream
     */
    public static InputStream toInputStream(final CharSequence input) {
        return toInputStream(input, Charset.defaultCharset());
    }

    /**
     * 将 字符串转成流
     *
     * @param input    the CharSequence to convert
     * @param encoding the encoding to use, null means platform default
     * @return an input stream
     */
    public static InputStream toInputStream(final CharSequence input, final Charset encoding) {
        return toInputStream(input.toString(), encoding);
    }

    /**
     * 将 字符串转成流
     *
     * @param input    the CharSequence to convert
     * @param encoding the encoding to use, null means platform default
     * @return an input stream
     * @throws IOException                                  if the encoding is invalid
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static InputStream toInputStream(final CharSequence input, final String encoding) throws IOException {
        return toInputStream(input, Charsets.toCharset(encoding));
    }

    //-----------------------------------------------------------------------

    /**
     * 将 字符串转成流
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param input the string to convert
     * @return an input stream
     */
    public static InputStream toInputStream(final String input) {
        return toInputStream(input, Charset.defaultCharset());
    }

    /**
     * 将 字符串转成流
     *
     * @param input    the string to convert
     * @param encoding the encoding to use, null means platform default
     * @return an input stream
     */
    public static InputStream toInputStream(final String input, final Charset encoding) {
        return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(encoding)));
    }

    /**
     * 将 字符串转成流
     *
     * @param input    the string to convert
     * @param encoding the encoding to use, null means platform default
     * @return an input stream
     * @throws IOException                                  if the encoding is invalid
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static InputStream toInputStream(final String input, final String encoding) throws IOException {
        final byte[] bytes = input.getBytes(Charsets.toCharset(encoding));
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 将文件转成文件流
     *
     * @param file
     * @return an input stream
     * @throws IOException
     */
    public static FileInputStream toInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    // write byte[]
    //-----------------------------------------------------------------------

    /**
     * 将 bytes 数组写入 OutputStream
     *
     * @param data   the byte array to write, do not modify during output,
     *               null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final byte[] data, final OutputStream output)
            throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    /**
     * 将bytes 数组写入 输出流，和{@link #write(byte[], OutputStream)}相比，该方法分块写入，适合非常大的bytes数组
     *
     * @param data   the byte array to write, do not modify during output,
     *               null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void writeChunked(final byte[] data, final OutputStream output)
            throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                final int chunk = Math.min(bytes, DEFAULT_BUFFER_SIZE);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    /**
     * 将 bytes 数组写入 Writer
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * <p>
     * 该方法用了 {@link String#String(byte[])}，可以使用{@link String#String(byte[])} 代替，然后 调用
     * {@link Writer#write(String)}
     *
     * @param data   the byte array to write, do not modify during output,
     *               null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final byte[] data, final Writer output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    /**
     * 将 bytes 数组写入 Writer
     *
     * @param data     the byte array to write, do not modify during output,
     *                 null ignored
     * @param output   the <code>Writer</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final byte[] data, final Writer output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(new String(data, Charsets.toCharset(encoding)));
        }
    }

    /**
     * 将 bytes 数组写入 Writer
     *
     * @param data     the byte array to write, do not modify during output,
     *                 null ignored
     * @param output   the <code>Writer</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException                         if output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static void write(final byte[] data, final Writer output, final String encoding) throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    /**
     * 将 bytes 数组写入 文件中,
     * <p>
     * <b>注意:<b/> 是覆盖写
     *
     * @param data The binary data to write
     * @param file The file to write
     * @throws IOException
     */
    public static void write(byte[] data, File file) throws IOException {
        write(data, file, false);
    }


    /**
     * 将 bytes 数组写入 文件中,可以设置追加写
     *
     * @param data The binary data to write
     * @param file The file to write
     * @throws IOException
     */
    public static void write(byte[] data, File file, boolean append) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file, append);
            os.write(data);
            os.flush();
        } finally {
            close(os);
        }
    }

    // write char[]
    //-----------------------------------------------------------------------

    /**
     * 将 chars 数组写入 <code>Writer</code>
     *
     * @param data   the char array to write, do not modify during output,
     *               null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final char[] data, final Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    /**
     * 将 chars 数组写入 <code>Writer</code>
     * 使用了分块写入,适用于 char 数组很大的情况。避免分配太大内存块
     *
     * @param data   the char array to write, do not modify during output,
     *               null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void writeChunked(final char[] data, final Writer output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                final int chunk = Math.min(bytes, DEFAULT_BUFFER_SIZE);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    /**
     * 将 chars 数组写入 <code>OutputStream</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param data   the char array to write, do not modify during output,
     *               null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final char[] data, final OutputStream output)
            throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    /**
     * 将 chars 数组写入 <code>OutputStream</code>
     *
     * @param data     the char array to write, do not modify during output,
     *                 null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final char[] data, final OutputStream output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(new String(data).getBytes(Charsets.toCharset(encoding)));
        }
    }

    /**
     * 将 chars 数组写入 <code>OutputStream</code>
     *
     * @param data     the char array to write, do not modify during output,
     *                 null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException                         if output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the encoding is not supported.
     */
    public static void write(final char[] data, final OutputStream output, final String encoding)
            throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    // write CharSequence
    //-----------------------------------------------------------------------

    /**
     * 将CharSequence字符串写入到 <code>Writer</code>
     *
     * @param data   the <code>CharSequence</code> to write, null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final CharSequence data, final Writer output) throws IOException {
        if (data != null) {
            write(data.toString(), output);
        }
    }

    /**
     * 将CharSequence字符串写入到 <code>OutputStream</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * <p>
     * 这个方法使用到了{@link String#getBytes()}.
     *
     * @param data   the <code>CharSequence</code> to write, null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final CharSequence data, final OutputStream output)
            throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    /**
     * 将CharSequence字符串写入到 <code>OutputStream</code>
     * <p>
     * 这个方法使用到了{@link String#getBytes()}.
     *
     * @param data     the <code>CharSequence</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final CharSequence data, final OutputStream output, final Charset encoding)
            throws IOException {
        if (data != null) {
            write(data.toString(), output, encoding);
        }
    }

    /**
     * 将CharSequence字符串写入到 <code>OutputStream</code>
     * <p>
     * 这个方法使用到了{@link String#getBytes()}.
     *
     * @param data     the <code>CharSequence</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException                         if output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the encoding is not supported.
     */
    public static void write(final CharSequence data, final OutputStream output, final String encoding)
            throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    // write String
    //-----------------------------------------------------------------------

    /**
     * 将String字符串写入到 <code>Writer</code>
     *
     * @param data   the <code>String</code> to write, null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final String data, final Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    /**
     * 将 String 字符串写入到 <code>OutputStream</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * <p>
     * 这个方法使用到了{@link String#getBytes()}.
     *
     * @param data   the <code>String</code> to write, null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final String data, final OutputStream output)
            throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    /**
     * 将 String 字符串写入到 <code>OutputStream</code>
     * <p>
     * 这个方法使用到了 {@link String#getBytes(String)}.
     *
     * @param data     the <code>String</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(Charsets.toCharset(encoding)));
        }
    }

    /**
     * 将 String 字符串写入到 <code>OutputStream</code>
     *
     * @param data     the <code>String</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException                         if output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the encoding is not supported.
     */
    public static void write(final String data, final OutputStream output, final String encoding)
            throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    // write StringBuffer
    //-----------------------------------------------------------------------

    /**
     * 将 StringBuffer 写入到 <code>Writer</code>
     *
     * @param data   the <code>StringBuffer</code> to write, null ignored
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     * @see #write(CharSequence, Writer)
     */
    public static void write(final StringBuffer data, final Writer output)
            throws IOException {
        if (data != null) {
            output.write(data.toString());
        }
    }

    /**
     * 将 StringBuffer 写入到 <code>OutputStream</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param data   the <code>StringBuffer</code> to write, null ignored
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     * @see #write(CharSequence, OutputStream)
     */
    public static void write(final StringBuffer data, final OutputStream output)
            throws IOException {
        write(data, output, (String) null);
    }

    /**
     * 将 StringBuffer 写入到 <code>OutputStream</code>
     *
     * @param data     the <code>StringBuffer</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException                         if output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the encoding is not supported.
     * @see #write(CharSequence, OutputStream, String)
     */
    public static void write(final StringBuffer data, final OutputStream output, final String encoding)
            throws IOException {
        if (data != null) {
            output.write(data.toString().getBytes(Charsets.toCharset(encoding)));
        }
    }

    // write stream to another stream
    //-----------------------------------------------------------------------

    /**
     * Write stream to another stream.
     *
     * @param is The stream to read
     * @param os The stream to write
     * @throws IOException IOException
     */
    public static void write(InputStream is, OutputStream os) throws IOException {
        write(is, true, os, true);
    }

    /**
     * Write stream to another stream.
     *
     * @param is                The stream to read
     * @param closeInputStream  whether to close input stream
     * @param os                The stream to write
     * @param closeOutputStream whether to close output stream
     * @throws IOException IOException
     */
    public static void write(InputStream is, boolean closeInputStream, OutputStream os,
                             boolean closeOutputStream) throws IOException {
        try {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int count;
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } finally {
            if (closeInputStream) {
                close(is);
            }
            if (closeOutputStream) {
                close(os);
            }
        }
    }

    // write stream to a file
    //-----------------------------------------------------------------------

    /**
     * Write stream to a file.
     *
     * @param is   The stream to read
     * @param file The file to write
     * @throws IOException
     */
    public static void write(InputStream is, File file) throws IOException {
        write(is, file, false);
    }

    /**
     * Write stream to a file.
     *
     * @param is   The stream to read
     * @param file The file to write
     * @throws IOException
     */
    public static void write(InputStream is, File file, boolean append) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file, append);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int count;
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } finally {
            close(is);
            close(os);
        }
    }


    // writeLines
    //-----------------------------------------------------------------------

    /**
     * 将集合中的元素按字符串一行一行的写入<code>OutputStream</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * 建议: 参数lines 中的 元素实现 toString() 方法
     *
     * @param lines      the lines to write, null entries produce blank lines
     * @param lineEnding the line separator to use, null is system default
     * @param output     the <code>OutputStream</code> to write to, not null, not closed
     * @throws NullPointerException if the output is null
     * @throws IOException          if an I/O error occurs
     * @see #writeLines(Collection, String, OutputStream, Charset)
     */
    public static void writeLines(final Collection<?> lines, final String lineEnding,
                                  final OutputStream output) throws IOException {
        writeLines(lines, lineEnding, output, Charset.defaultCharset());
    }

    /**
     * 将集合中的元素按字符串一行一行的写入<code>OutputStream</code>
     * 建议: 参数lines 中的 元素实现 toString() 方法
     *
     * @param lines      the lines to write, null entries produce blank lines
     * @param lineEnding the line separator to use, null is system default
     * @param output     the <code>OutputStream</code> to write to, not null, not closed
     * @param encoding   the encoding to use, null means platform default
     * @throws NullPointerException if the output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void writeLines(final Collection<?> lines, String lineEnding, final OutputStream output,
                                  final Charset encoding) throws IOException {
        if (lines == null) {
            return;
        }
        if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
        }
        final Charset cs = Charsets.toCharset(encoding);
        for (final Object line : lines) {
            if (line != null) {
                output.write(line.toString().getBytes(cs));
            }
            output.write(lineEnding.getBytes(cs));
        }
    }

    /**
     * 将集合中的元素按字符串一行一行的写入<code>OutputStream</code>
     * 建议: 参数lines 中的 元素实现 toString() 方法
     *
     * @param lines      the lines to write, null entries produce blank lines
     * @param lineEnding the line separator to use, null is system default
     * @param output     the <code>OutputStream</code> to write to, not null, not closed
     * @param encoding   the encoding to use, null means platform default
     * @throws NullPointerException                         if the output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static void writeLines(final Collection<?> lines, final String lineEnding,
                                  final OutputStream output, final String encoding) throws IOException {
        writeLines(lines, lineEnding, output, Charsets.toCharset(encoding));
    }

    /**
     * 将集合中的元素按字符串一行一行的写入<code>Writer</code>
     * 建议: 参数lines 中的 元素实现 toString() 方法
     *
     * @param lines      the lines to write, null entries produce blank lines
     * @param lineEnding the line separator to use, null is system default
     * @param writer     the <code>Writer</code> to write to, not null, not closed
     * @throws NullPointerException if the input is null
     * @throws IOException          if an I/O error occurs
     */
    public static void writeLines(final Collection<?> lines, String lineEnding,
                                  final Writer writer) throws IOException {
        if (lines == null) {
            return;
        }
        if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
        }
        for (final Object line : lines) {
            if (line != null) {
                writer.write(line.toString());
            }
            writer.write(lineEnding);
        }
    }

    // copy from InputStream
    //-----------------------------------------------------------------------

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     * <p>
     * <b>注意:<b/> 如果 <code>InputStream</code> 流中 数据流比较大(超过了2GB)
     * 复制完成后返回的是 <code>-1</code>，如果关心 返回值，请使用{@link #copy(InputStream, OutputStream)}
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @return the number of bytes copied, or -1 if &gt; Integer.MAX_VALUE
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     * 并且使用 给定的buffer大小进行拷贝
     *
     * @param input      the <code>InputStream</code> to read from
     * @param output     the <code>OutputStream</code> to write to
     * @param bufferSize the bufferSize used to copy from the input to the output
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     * <p>
     * 拷贝的 buffer 大小为 {@link #DEFAULT_BUFFER_SIZE}.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @param buffer the buffer to use for the copy
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     * 在拷贝前会自动跳过 <code>inputOffset</code> 字节
     * <p>
     * <b>注意:<b/> 该方法内部使用到了 {@link #skip(InputStream, long)} 来跳过字节码，相比
     * {@link InputStream#skip(long)} 效率更低，以此来保证跳过正确的字节数
     * </p>
     * 拷贝的 buffer 大小为 {@link #DEFAULT_BUFFER_SIZE}.
     *
     * @param input       the <code>InputStream</code> to read from
     * @param output      the <code>OutputStream</code> to write to
     * @param inputOffset : number of bytes to skip from input before copying
     *                    -ve values are ignored
     * @param length      : number of bytes to copy. -ve means all
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final InputStream input, final OutputStream output, final long inputOffset,
                                 final long length) throws IOException {
        return copyLarge(input, output, inputOffset, length, new byte[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * 将 <code>InputStream</code> 中的 字节码拷贝到 <code>OutputStream</code>.
     * 在拷贝前会自动跳过 <code>inputOffset</code> 字节
     * <p>
     * <b>注意:<b/> 该方法内部使用到了 {@link #skip(InputStream, long)} 来跳过字节码，相比
     * {@link InputStream#skip(long)} 效率更低，以此来保证跳过正确的字节数
     * </p>
     *
     * @param input       the <code>InputStream</code> to read from
     * @param output      the <code>OutputStream</code> to write to
     * @param inputOffset : number of bytes to skip from input before copying
     *                    -ve values are ignored
     * @param length      : number of bytes to copy. -ve means all
     * @param buffer      the buffer to use for the copy
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final InputStream input, final OutputStream output,
                                 final long inputOffset, final long length, final byte[] buffer) throws IOException {
        if (inputOffset > 0) {
            skipFully(input, inputOffset);
        }
        if (length == 0) {
            return 0;
        }
        final int bufferLength = buffer.length;
        int bytesToRead = bufferLength;
        if (length > 0 && length < bufferLength) {
            bytesToRead = (int) length;
        }
        int read;
        long totalRead = 0;
        while (bytesToRead > 0 && EOF != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += read;
            if (length > 0) { // only adjust length if not reading to the end
                // Note the cast must work because buffer.length is an integer
                bytesToRead = (int) Math.min(length - totalRead, bufferLength);
            }
        }
        return totalRead;
    }

    /**
     * 将 <code>InputStream</code> 中的字节码 拷贝到 <code>Writer</code>
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     * <p>
     * 该方法内部实现使用了 {@link InputStreamReader}.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     * @see #copy(InputStream, Writer, Charset)
     */
    public static void copy(final InputStream input, final Writer output)
            throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    /**
     * 将 <code>InputStream</code> 中的字节码 拷贝到 <code>Writer</code>
     * 该方法内部实现使用了 {@link InputStreamReader}.
     *
     * @param input         the <code>InputStream</code> to read from
     * @param output        the <code>Writer</code> to write to
     * @param inputEncoding the encoding to use for the input stream, null means platform default
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void copy(final InputStream input, final Writer output, final Charset inputEncoding)
            throws IOException {
        final InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(inputEncoding));
        copy(in, output);
    }

    /**
     * 将 <code>InputStream</code> 中的字节码 拷贝到 <code>Writer</code>
     * <p>
     * 该方法内部实现使用了 {@link InputStreamReader}.
     *
     * @param input         the <code>InputStream</code> to read from
     * @param output        the <code>Writer</code> to write to
     * @param inputEncoding the encoding to use for the InputStream, null means platform default
     * @throws NullPointerException                         if the input or output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static void copy(final InputStream input, final Writer output, final String inputEncoding)
            throws IOException {
        copy(input, output, Charsets.toCharset(inputEncoding));
    }

    // copy from Reader
    //-----------------------------------------------------------------------

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>Writer</code>
     * <p>
     * 如果 输入的数据流比较大(大于2GB) 返回值会返回-1，如果 在意返回值，请使用
     * {@link #copyLarge(Reader, Writer)}
     *
     * @param input  the <code>Reader</code> to read from
     * @param output the <code>Writer</code> to write to
     * @return the number of characters copied, or -1 if &gt; Integer.MAX_VALUE
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static int copy(final Reader input, final Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>Writer</code>
     * <p>
     * 默认的 buffer 大小为{@link #DEFAULT_BUFFER_SIZE}.
     *
     * @param input  the <code>Reader</code> to read from
     * @param output the <code>Writer</code> to write to
     * @return the number of characters copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final Reader input, final Writer output) throws IOException {
        return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>Writer</code>
     *
     * @param input  the <code>Reader</code> to read from
     * @param output the <code>Writer</code> to write to
     * @param buffer the buffer to be used for the copy
     * @return the number of characters copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>Writer</code>.
     * 拷贝前会跳过指定的 字符数
     *
     * @param input       the <code>Reader</code> to read from
     * @param output      the <code>Writer</code> to write to
     * @param inputOffset : number of chars to skip from input before copying
     *                    -ve values are ignored
     * @param length      : number of chars to copy. -ve means all
     * @return the number of chars copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final Reader input, final Writer output, final long inputOffset, final long length)
            throws IOException {
        return copyLarge(input, output, inputOffset, length, new char[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>Writer</code>.
     * 拷贝前会跳过指定的 字符数
     *
     * @param input       the <code>Reader</code> to read from
     * @param output      the <code>Writer</code> to write to
     * @param inputOffset : number of chars to skip from input before copying
     *                    -ve values are ignored
     * @param length      : number of chars to copy. -ve means all
     * @param buffer      the buffer to be used for the copy
     * @return the number of chars copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final Reader input, final Writer output, final long inputOffset, final long length,
                                 final char[] buffer)
            throws IOException {
        if (inputOffset > 0) {
            skipFully(input, inputOffset);
        }
        if (length == 0) {
            return 0;
        }
        int bytesToRead = buffer.length;
        if (length > 0 && length < buffer.length) {
            bytesToRead = (int) length;
        }
        int read;
        long totalRead = 0;
        while (bytesToRead > 0 && EOF != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += read;
            if (length > 0) { // only adjust length if not reading to the end
                // Note the cast must work because buffer.length is an integer
                bytesToRead = (int) Math.min(length - totalRead, buffer.length);
            }
        }
        return totalRead;
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>OutputStream</code>.
     * <p>
     * 方法内部实现中使用到了 {@link OutputStreamWriter}.
     * <p>
     * <b>注意:<b/> 使用系统默认的编码格式
     *
     * @param input  the <code>Reader</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     * @see #copy(Reader, OutputStream, Charset)
     */
    public static void copy(final Reader input, final OutputStream output)
            throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>OutputStream</code>.
     *
     * @param input          the <code>Reader</code> to read from
     * @param output         the <code>OutputStream</code> to write to
     * @param outputEncoding the encoding to use for the OutputStream, null means platform default
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void copy(final Reader input, final OutputStream output, final Charset outputEncoding)
            throws IOException {
        final OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(outputEncoding));
        copy(input, out);
        // XXX Unless anyone is planning on rewriting OutputStreamWriter,
        // we have to flush here.
        out.flush();
    }

    /**
     * 将 <code>Reader</code> 中的 chars 字节数组 拷贝到 <code>OutputStream</code>.
     *
     * @param input          the <code>Reader</code> to read from
     * @param output         the <code>OutputStream</code> to write to
     * @param outputEncoding the encoding to use for the OutputStream, null means platform default
     * @throws NullPointerException                         if the input or output is null
     * @throws IOException                                  if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     */
    public static void copy(final Reader input, final OutputStream output, final String outputEncoding)
            throws IOException {
        copy(input, output, Charsets.toCharset(outputEncoding));
    }

    // content equals
    //-----------------------------------------------------------------------

    /**
     * 比较两个流是否相同
     * Compares the contents of two Streams to determine if they are equal or
     * not.
     * <p>
     * <p>
     * 方法内部实现使用了 <code>BufferedInputStream</code>,所以不用再包装成 <code>BufferedInputStream</code>
     *
     * @param input1 the first stream
     * @param input2 the second stream
     * @return true if the content of the streams are equal or they both don't
     * exist, false otherwise
     * @throws NullPointerException if either input is null
     * @throws IOException          if an I/O error occurs
     */
    public static boolean contentEquals(InputStream input1, InputStream input2)
            throws IOException {
        if (input1 == input2) {
            return true;
        }
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }

        int ch = input1.read();
        while (EOF != ch) {
            final int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }

        final int ch2 = input2.read();
        return ch2 == EOF;
    }

    /**
     * 比较两个 Reader 字符流是否相同
     *
     * @param input1 the first reader
     * @param input2 the second reader
     * @return true if the content of the readers are equal or they both don't
     * exist, false otherwise
     * @throws NullPointerException if either input is null
     * @throws IOException          if an I/O error occurs
     */
    public static boolean contentEquals(Reader input1, Reader input2)
            throws IOException {
        if (input1 == input2) {
            return true;
        }

        input1 = toBufferedReader(input1);
        input2 = toBufferedReader(input2);

        int ch = input1.read();
        while (EOF != ch) {
            final int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }

        final int ch2 = input2.read();
        return ch2 == EOF;
    }

    /**
     * 比较两个 Reader 字符流是否相同,并且忽略掉结尾标识符
     *
     * @param input1 the first reader
     * @param input2 the second reader
     * @return true if the content of the readers are equal (ignoring EOL differences),  false otherwise
     * @throws NullPointerException if either input is null
     * @throws IOException          if an I/O error occurs
     */
    public static boolean contentEqualsIgnoreEOL(final Reader input1, final Reader input2)
            throws IOException {
        if (input1 == input2) {
            return true;
        }
        final BufferedReader br1 = toBufferedReader(input1);
        final BufferedReader br2 = toBufferedReader(input2);

        String line1 = br1.readLine();
        String line2 = br2.readLine();
        while (line1 != null && line2 != null && line1.equals(line2)) {
            line1 = br1.readLine();
            line2 = br2.readLine();
        }
        return line1 == null ? line2 == null ? true : false : line1.equals(line2);
    }

    /**
     * 跳过 inputStream 中的 部分字节。
     * 注意该方法并不是委托调用{@link InputStream#skip(long)},
     * 而是 使用 {@link InputStream#read(byte[], int, int)} 读出相应字节。
     * <p>
     * 这是为了fix 一个问题：<a href="https://issues.apache.org/jira/browse/IO-203">IO-203 - Add skipFully() method for InputStreams</a>
     * <p>
     * 和 {@link InputStream#skip(long)} 相比，该方法尽可能读出更多的字节。
     * 该方法有可能比 调用 {@link InputStream#skip(long)} 效率更低，以此来保证跳过正确的字节数
     *
     * @param input  byte stream to skip
     * @param toSkip number of bytes to skip.
     * @return number of bytes actually skipped.
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @see InputStream#skip(long)
     * @see <a href="https://issues.apache.org/jira/browse/IO-203">IO-203 - Add skipFully() method for InputStreams</a>
     */
    public static long skip(final InputStream input, final long toSkip) throws IOException {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        /*
         * N.B. no need to synchronize this because: - we don't care if the buffer is created multiple times (the data
         * is ignored) - we always use the same size buffer, so if it it is recreated it will still be OK (if the buffer
         * size were variable, we would need to synch. to ensure some other thread did not create a smaller one)
         */
        if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[SKIP_BUFFER_SIZE];
        }
        long remain = toSkip;
        while (remain > 0) {
            // See https://issues.apache.org/jira/browse/IO-203 for why we use read() rather than delegating to skip()
            final long n = input.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(remain, SKIP_BUFFER_SIZE));
            if (n < 0) { // EOF
                break;
            }
            remain -= n;
        }
        return toSkip - remain;
    }

    /**
     * 跳过 ReadableByteChannel 中的 部分字节。
     *
     * @param input  ReadableByteChannel to skip
     * @param toSkip number of bytes to skip.
     * @return number of bytes actually skipped.
     * @throws IOException              if there is a problem reading the ReadableByteChannel
     * @throws IllegalArgumentException if toSkip is negative
     */
    public static long skip(final ReadableByteChannel input, final long toSkip) throws IOException {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        final ByteBuffer skipByteBuffer = ByteBuffer.allocate((int) Math.min(toSkip, SKIP_BUFFER_SIZE));
        long remain = toSkip;
        while (remain > 0) {
            skipByteBuffer.position(0);
            skipByteBuffer.limit((int) Math.min(remain, SKIP_BUFFER_SIZE));
            final int n = input.read(skipByteBuffer);
            if (n == EOF) {
                break;
            }
            remain -= n;
        }
        return toSkip - remain;
    }

    /**
     * 跳过 <code>Reader<code/> 中的 部分字符
     * Skips characters from an input character stream.
     * This implementation guarantees that it will read as many characters
     * as possible before giving up; this may not always be the case for
     * skip() implementations in subclasses of {@link Reader}.
     * <p>
     * <b>注意:<b/> 内部实现使用了 {@link Reader#read(char[], int, int)} 来代替 {@link Reader#skip(long)} 跳过字符，
     * 这是为了保证正确跳过字符数。所以效率会低一些
     *
     * @param input  character stream to skip
     * @param toSkip number of characters to skip.
     * @return number of characters actually skipped.
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @see Reader#skip(long)
     * @see <a href="https://issues.apache.org/jira/browse/IO-203">IO-203 - Add skipFully() method for InputStreams</a>
     */
    public static long skip(final Reader input, final long toSkip) throws IOException {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        /*
         * N.B. no need to synchronize this because: - we don't care if the buffer is created multiple times (the data
         * is ignored) - we always use the same size buffer, so if it it is recreated it will still be OK (if the buffer
         * size were variable, we would need to synch. to ensure some other thread did not create a smaller one)
         */
        if (SKIP_CHAR_BUFFER == null) {
            SKIP_CHAR_BUFFER = new char[SKIP_BUFFER_SIZE];
        }
        long remain = toSkip;
        while (remain > 0) {
            // See https://issues.apache.org/jira/browse/IO-203 for why we use read() rather than delegating to skip()
            final long n = input.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(remain, SKIP_BUFFER_SIZE));
            if (n < 0) { // EOF
                break;
            }
            remain -= n;
        }
        return toSkip - remain;
    }

    /**
     * 跳过<code>InputStream</code>中的指定长度的字节
     *
     * @param input  stream to skip
     * @param toSkip the number of bytes to skip
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @throws EOFException             if the number of bytes skipped was incorrect
     * @see InputStream#skip(long)
     */
    public static void skipFully(final InputStream input, final long toSkip) throws IOException {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        final long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    /**
     * 跳过<code>ReadableByteChannel</code>中的指定长度的字节
     *
     * @param input  ReadableByteChannel to skip
     * @param toSkip the number of bytes to skip
     * @throws IOException              if there is a problem reading the ReadableByteChannel
     * @throws IllegalArgumentException if toSkip is negative
     * @throws EOFException             if the number of bytes skipped was incorrect
     */
    public static void skipFully(final ReadableByteChannel input, final long toSkip) throws IOException {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        final long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    /**
     * 跳过<code>input</code>中的指定长度的字节
     *
     * @param input  stream to skip
     * @param toSkip the number of characters to skip
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @throws EOFException             if the number of characters skipped was incorrect
     * @see Reader#skip(long)
     */
    public static void skipFully(final Reader input, final long toSkip) throws IOException {
        final long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
        }
    }


    /**
     * 将指定长度的字符数组从 <code>Reader</code> 中读到 <code>char[]</code>
     * 在读之前会跳过指定的长度
     *
     * @param input  where to read input from
     * @param buffer destination
     * @param offset initial offset into buffer
     * @param length length to read, must be &gt;= 0
     * @return actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
    public static int read(final Reader input, final char[] buffer, final int offset, final int length)
            throws IOException {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        int remaining = length;
        while (remaining > 0) {
            final int location = length - remaining;
            final int count = input.read(buffer, offset + location, remaining);
            if (EOF == count) { // EOF
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }

    /**
     * 将所有的字符数组从 <code>Reader</code> 中读到 <code>char[]</code>
     *
     * @param input  where to read input from
     * @param buffer destination
     * @return actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
    public static int read(final Reader input, final char[] buffer) throws IOException {
        return read(input, buffer, 0, buffer.length);
    }

    /**
     * 将指定长度的字符数组从 <code>Reader</code> 中读到 <code>char[]</code>
     * 在读之前会跳过指定的长度
     *
     * @param input  where to read input from
     * @param buffer destination
     * @param offset initial offset into buffer
     * @param length length to read, must be &gt;= 0
     * @return actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
    public static int read(final InputStream input, final byte[] buffer, final int offset, final int length)
            throws IOException {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        int remaining = length;
        while (remaining > 0) {
            final int location = length - remaining;
            final int count = input.read(buffer, offset + location, remaining);
            if (EOF == count) { // EOF
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }

    /**
     * 将 <code>InputStream</code> 中的字节码 全部读到 <code>byte[]</code> 中
     *
     * @param input  where to read input from
     * @param buffer destination
     * @return actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
    public static int read(final InputStream input, final byte[] buffer) throws IOException {
        return read(input, buffer, 0, buffer.length);
    }

    /**
     * 将 <code>ReadableByteChannel</code> 中的字节码 全部读到 <code>ByteBuffer</code> 中
     *
     * @param input  the byte channel to read
     * @param buffer byte buffer destination
     * @return the actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
    public static int read(final ReadableByteChannel input, final ByteBuffer buffer) throws IOException {
        final int length = buffer.remaining();
        while (buffer.remaining() > 0) {
            final int count = input.read(buffer);
            if (EOF == count) { // EOF
                break;
            }
        }
        return length - buffer.remaining();
    }

    /**
     * 将 <code>Reader</code> 中的字符数组 按照指定的长度和跳过的长度读到 <code>char[]</code> 中
     *
     * @param input  where to read input from
     * @param buffer destination
     * @param offset initial offset into buffer
     * @param length length to read, must be &gt;= 0
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if length is negative
     * @throws EOFException             if the number of characters read was incorrect
     */
    public static void readFully(final Reader input, final char[] buffer, final int offset, final int length)
            throws IOException {
        final int actual = read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    /**
     * 将 <code>Reader</code> 中的字符数组 全部读到 <code>char[]</code> 中
     *
     * @param input  where to read input from
     * @param buffer destination
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if length is negative
     * @throws EOFException             if the number of characters read was incorrect
     */
    public static void readFully(final Reader input, final char[] buffer) throws IOException {
        readFully(input, buffer, 0, buffer.length);
    }

    /**
     * 将 <code>InputStream</code> 中的字节数组 按照指定的长度和跳过的长度读到 <code>byte[]</code> 中
     *
     * @param input  where to read input from
     * @param buffer destination
     * @param offset initial offset into buffer
     * @param length length to read, must be &gt;= 0
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if length is negative
     * @throws EOFException             if the number of bytes read was incorrect
     */
    public static void readFully(final InputStream input, final byte[] buffer, final int offset, final int length)
            throws IOException {
        final int actual = read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    /**
     * 将 <code>InputStream</code> 中的字节数组 全部读到 <code>byte[]</code> 中
     *
     * @param input  where to read input from
     * @param buffer destination
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if length is negative
     * @throws EOFException             if the number of bytes read was incorrect
     */
    public static void readFully(final InputStream input, final byte[] buffer) throws IOException {
        readFully(input, buffer, 0, buffer.length);
    }

    /**
     * 从<code>InputStream</code>中读取给定长度的字节码返回
     *
     * @param input  where to read input from
     * @param length length to read, must be &gt;= 0
     * @return the bytes read from input
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if length is negative
     * @throws EOFException             if the number of bytes read was incorrect
     */
    public static byte[] readFully(final InputStream input, final int length) throws IOException {
        final byte[] buffer = new byte[length];
        readFully(input, buffer, 0, buffer.length);
        return buffer;
    }

    /**
     * 将 <code>ReadableByteChannel</code> 中的字节数组 读到 <code>ByteBuffer</code> 中
     *
     * @param input  the byte channel to read
     * @param buffer byte buffer destination
     * @throws IOException  if there is a problem reading the file
     * @throws EOFException if the number of bytes read was incorrect
     */
    public static void readFully(final ReadableByteChannel input, final ByteBuffer buffer) throws IOException {
        final int expected = buffer.remaining();
        final int actual = read(input, buffer);
        if (actual != expected) {
            throw new EOFException("Length to read: " + expected + " actual: " + actual);
        }
    }

}
