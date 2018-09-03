package com.elevenst.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML tag 제거 유틸
 */
public class StripHTMLTag {
    /**
     * &lt;script&gt;
     */
    private static final Pattern HTML_SCRIPT        = Pattern.compile("\\<script[^>]*?>(.|\\n)*?\\<\\/script\\>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;script/&gt;
     */
    private static final Pattern HTML_SCRIPT_START  = Pattern.compile("\\<script[^>]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    private static final Pattern HTML_SCRIPT_END    = Pattern.compile("\\<\\/script\\>", Pattern.CASE_INSENSITIVE );
    /**
     * &lt;style&gt;
     */
    private static final Pattern HTML_STYLE         = Pattern.compile("\\<style[^>]*?>(.|\\n)*?\\<\\/style\\>",  Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;style/&gt;
     */
    private static final Pattern HTML_STYLE_START   = Pattern.compile("\\<style[^>]*?>",  Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    private static final Pattern HTML_STYLE_END     = Pattern.compile("\\<\\/style\\>",  Pattern.CASE_INSENSITIVE );
    /**
     * &lt;iframe&gt;
     */
    private static final Pattern HTML_IFRAME        = Pattern.compile("\\<iframe[^>]*?>(.|\\n)*?\\<\\/iframe\\>",  Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;iframe/&gt;
     */
    private static final Pattern HTML_IFRAME_START  = Pattern.compile("\\<iframe[^>]*?>",  Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    private static final Pattern HTML_IFRAME_END     = Pattern.compile("\\<\\/iframe\\>", Pattern.CASE_INSENSITIVE);
    /**
     * &lt;object&gt;
     */
    private static final Pattern HTML_OBJECT        = Pattern.compile("\\<object[^>]*?>(.|\\n)*?\\<\\/object\\>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;object/&gt;
     */
    private static final Pattern HTML_OBJECT_START  = Pattern.compile("\\<object[^>]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    private static final Pattern HTML_OBJECT_END    = Pattern.compile("\\<\\/object\\>", Pattern.CASE_INSENSITIVE);
    /**
     * &lt;embed&gt;
     */
    private static final Pattern HTML_EMBED         = Pattern.compile("\\<embed[^>]*?>(.|\\n)*?\\<\\/embed\\>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;embed/&gt;
     */
    private static final Pattern HTML_EMBED_SINGLE  = Pattern.compile("\\<embed[^>]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    /**
     * &lt;form&gt;
     */
    private static final Pattern HTML_FORM_START    = Pattern.compile("\\<form[^>]*?>",  Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
    /**
     * &lt;/form&gt;
     */
    private static final Pattern HTML_FORM_END      = Pattern.compile("\\<\\/form\\>", Pattern.CASE_INSENSITIVE);
    /**
     * &lt;applet&gt;
     */
    private static final Pattern HTML_APPLET        = Pattern.compile("\\<applet[^>]*?>(.|\\n)*?\\<\\/applet\\>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
    /**
     * &lt;applet/&gt;
     */
    private static final Pattern HTML_APPLET_SINGLE = Pattern.compile("\\<applet[^>]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

    /**
     * &lt;iframe&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;iframe&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripIframe( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_IFRAME.matcher( sourceHtml ).replaceAll("");
        result = HTML_IFRAME_START.matcher( result ).replaceAll("");
        result = HTML_IFRAME_END.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;object&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;object&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripObject( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_OBJECT.matcher( sourceHtml ).replaceAll("");
        result = HTML_OBJECT_START.matcher( result ).replaceAll("");
        result = HTML_OBJECT_END.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;script&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;script&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripScript( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_SCRIPT.matcher( sourceHtml ).replaceAll("");
        result = HTML_SCRIPT_START.matcher( result ).replaceAll("");
        result = HTML_SCRIPT_END.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;style&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;style&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripStyle( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_STYLE.matcher( sourceHtml ).replaceAll("");
        result = HTML_STYLE_START.matcher( result ).replaceAll("");
        result = HTML_STYLE_END.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;embed&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;embed&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripEmbed( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_EMBED.matcher( sourceHtml ).replaceAll("");
        result = HTML_EMBED_SINGLE.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;applet&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;applet&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripApplet( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_APPLET.matcher( sourceHtml ).replaceAll("");
        result = HTML_APPLET_SINGLE.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * &lt;form&gt; tag 를 제거한다.
     *
     * @param sourceHtml
     * @return &lt;applet&gt; 로 감싸여져있던 내용이 제거된 문자열.
     */
    public static String stripForm( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = HTML_FORM_START.matcher( sourceHtml ).replaceAll("");
        result = HTML_FORM_END.matcher( result ).replaceAll("");

        return result;
    }

    /**
     * StripHTMLTag Util 에서 제거할 수 있는
     * 모든 Tag(&lt;SCRIPT&gt;, &lt;OBJECT&gt;, &lt;APPLET&gt;, &lt;EMBED&gt;, &lt;FORM&gt;, &lt;IFRAME&gt;) 를
     * 제거한다.
     *
     * @param sourceHtml
     * @return tag &lt;SCRIPT&gt;, &lt;OBJECT&gt;, &lt;APPLET&gt;, &lt;EMBED&gt;, &lt;FORM&gt;, &lt;IFRAME&gt; tag 가 제거된 문자열
     */
    public static String stripAll( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;

        result = StripHTMLTag.stripScript( sourceHtml );
        result = StripHTMLTag.stripObject( result );
        result = StripHTMLTag.stripApplet( result );
        result = StripHTMLTag.stripEmbed( result );
        result = StripHTMLTag.stripForm( result );
        result = StripHTMLTag.stripIframe( result );

        return result;
    }

    private static final String _XSS_KEYWOARD_ATTR = "onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|"
        + "onbeforedeactivate|onbeforeeditfocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onclick|"
        + "oncontextmenu|oncontrolselect|oncopy|oncut|ondatavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|"
        + "ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror|onerrorupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|"
        + "onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmouseout|onmouseover|onmouseup|"
        + "onmousewheel|onmove|onmoveend|onmovestart|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizeend|onresizestart|onrowenter|"
        + "onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload";

    // 판마재서비스센터, 보안팀 XSS조치 사항, 키워드 삭제( html 테그 입력가능한 화면이라 키워드 삭제)
    public static String removeKeywoard( String sourceHtml ) {
        if( sourceHtml == null ) return sourceHtml;

        String result = null;
        Matcher m;

        Pattern key = Pattern.compile("(" + _XSS_KEYWOARD_ATTR + ")\\s*=[a-zA-Z\\.\\(\\)\\s\'\"]*" , Pattern.CASE_INSENSITIVE|Pattern.DOTALL);

        m = key.matcher(sourceHtml);
        result = m.replaceAll("");

        return result;
    }

    private static final String _HTML_TAGS = "html|head|body|meta|link|a|div|br|p|span|strong|b|table|tr|th|td|thead|tbody|img|h|hr|font";
    private static final String _HTML_SECTIONTAGS = "script|style|iframe|object|embed|form";

    /**
     * script|style|iframe|object|embed|form 태그 사용 구간 제거 및
     * html|head|body|meta|link|a|div|br|p|span|strong|b|table|tr|th|td|thead|tbody|img|h|hr|font 태그 사용시 제거
     * Html Entity Ref 제거
     *
     * @param sourceHtml
     * @return 정의된 태그가 제거된 문자열
     */
    public static  String removeInvalidTag(String sourceHtml) {
        return removeInvalidTag(sourceHtml, false, _HTML_SECTIONTAGS, _HTML_TAGS);
    }

    /**
     * script|style|iframe|object|embed|form 태그 사용 구간 제거 및
     * html|head|body|meta|link|a|div|br|p|span|strong|b|table|tr|th|td|thead|tbody|img|h|hr|font 태그 사용시 제거
     * Html Entity Ref 제거
     *
     * @param sourceHtml
     * @param useEntity  HTML Entity Ref 사용여부 (false : Html Entity Ref 제거)
     * @return 정의된 태그가 제거된 문자열
     */
    public static  String removeInvalidTag(String sourceHtml, boolean useEntity) {
        return removeInvalidTag(sourceHtml, useEntity, _HTML_SECTIONTAGS, _HTML_TAGS);
    }

    /**
     * 구간 제거할 태그 제거 및
     * html|head|body|meta|link|a|div|br|p|span|strong|b|table|tr|th|td|thead|tbody|img|h|hr|font 태그 사용시 제거
     * Html Entity Ref 제거 (useEntity : false)
     *
     * @param sourceHtml
     * @param useEntity  HTML Entity Ref 사용여부 (false : Html Entity Ref 제거)
     * @param sectionTags  구간 제거할 태그 리스트 (default : script|style|iframe|object|embed|form )
     * @return 정의된 태그가 제거된 문자열
     */
    public static  String removeInvalidTag(String sourceHtml, boolean useEntity, String sectionTags) {
        if (StringUtil.isEmpty(sectionTags)) sectionTags = _HTML_SECTIONTAGS;

        return removeInvalidTag(sourceHtml, useEntity, sectionTags, _HTML_TAGS);
    }


    /**
     *
     * 구간 제거할 태그 제거 및 제거할 태그 사용시 제거
     * Html Entity Ref 제거 (useEntity : false)
     *
     * @param sourceHtml
     * @param useEntity     HTML Entity Ref 사용여부 (false : Html Entity Ref 제거)
     * @param sectionTags   구간 제거할 태그 리스트 (default : script|style|iframe|object|embed|form )
     * @param tags          제거할 태그 리스트 (default :
     *                              html|head|body|meta|link|a|div|br|p|span|strong|b|table|tr|th|td|thead|tbody|img|h|hr|font )
     * @return 정의된 태그가 제거된 문자열
     */
    public static  String removeInvalidTag(String sourceHtml, boolean useEntity, String sectionTags, String tags) {
        if (StringUtil.isEmpty(sourceHtml)) return sourceHtml;
        if (StringUtil.isEmpty(sectionTags)) sectionTags = _HTML_SECTIONTAGS;
        if (StringUtil.isEmpty(tags)) tags = _HTML_TAGS;

        Pattern SCRIPTS = Pattern.compile("<(no)?("+ sectionTags + ")[^>]*>.*?</(no)?(" + sectionTags+ ")>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
        Pattern TAGS = Pattern.compile("<(/)?("+ tags +")+(\\s.*?=.*?)?(/)?>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
        Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
        Pattern WHITESPACE = Pattern.compile("\\s\\s+");

        String result = null;
        Matcher m;

        m = SCRIPTS.matcher(sourceHtml);
        result = m.replaceAll("");
        m = TAGS.matcher(result);
        result = m.replaceAll("-");
        if (!useEntity) {
            m = ENTITY_REFS.matcher(result);
            result = m.replaceAll("");
        }
        //m = WHITESPACE.matcher(result);
        //result = m.replaceAll(" ");

        return result;
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println( StripHTMLTag.stripApplet( objectHtml.toString() ) );
        System.out.println( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println( StripHTMLTag.stripForm( formHtml.toString() ) );
        System.out.println( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println( StripHTMLTag.stripIframe( iframeHtml.toString() ) );
        System.out.println( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println( StripHTMLTag.stripAll( objectHtml.toString()+formHtml.toString()+iframeHtml.toString() ) );
    }

    private static StringBuilder iframeHtml;
    private static StringBuilder objectHtml;
    private static StringBuilder formHtml;

    static {
        iframeHtml = new StringBuilder();
        iframeHtml.append("test \n <iframe src='test.jsp'>\n\ntest\n\n</iframe>\ntest1234");


        objectHtml = new StringBuilder();
        objectHtml.append("<p>test</p>\n");
        objectHtml.append("<object \nlanguage=\"JavaScript\">\n");
        objectHtml.append("<!--\n");
        objectHtml.append("document.domain = \"naver.com\";\n");
        objectHtml.append("var userNID = \"(none)\";\n");
        objectHtml.append("var isstereo = false;\n");
        objectHtml.append("var userinput = 0;\n");
        objectHtml.append("var isLogin = 0;\n");
        objectHtml.append("var showLeftAd = 0;\n");
        objectHtml.append("var keytext = new Array();\n");
        objectHtml.append("var keystatus = 0;\n");
        objectHtml.append("\n");
        objectHtml.append("document.onmousedown = setPocket;\n");
        objectHtml.append("document.write(\"<iframe src='http://lcs.naver.com/u{\"+document.URL+\"}' width=0 height=0 frameborder=0></iframe>\n");
        objectHtml.append("//-->\n");
        objectHtml.append("</object>\n");
        objectHtml.append("<div id=\"footer\">\n");
        objectHtml.append("</div>");

        formHtml = new StringBuilder();
        formHtml.append("<form action='test.jsp' \nonsubmit=( this )>\n");
        formHtml.append("<script>var a='test'; alert(a);</script>\n");
        formHtml.append("한글적용?\n");
        formHtml.append("</form>");
    }
}
