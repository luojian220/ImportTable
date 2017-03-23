package com.luno.utils;

import com.luno.pojo.BookMarcDTO;
import com.luno.pojo.WaiYuLibrary;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarcCommon {

	private static Logger logger = Logger.getLogger(MarcCommon.class);

	public static String fgf = "■";
    private final static char FLDEND = (char)30; // 字段结束符
    private final static char RECEND = (char)29; // 记录结束符
    private final static char SUBFLD = (char)31; // 子字段指示符

    public static List<BookMarcDTO> getMarcInfo(String str) {
		List<BookMarcDTO> list = new ArrayList<BookMarcDTO>();

		try {
			byte[] bytes = str.getBytes("gbk");
			int dateLength = Integer.parseInt(getStr(bytes, 12, 5));// 获取缺省长度
			int count = 2;
			while (count * 12 + 2 <= dateLength) {
				BookMarcDTO marc = new BookMarcDTO();
				// 通过3、4、5组成的数据解析出该字段所存储的数据
				if (str.length() <= count * 12 + 3){
					break;
				}
				String field = getStr(bytes, count * 12, 3);// 三位代表字段编号
				if(field.equals("010")){
					int length = Integer.parseInt(getStr(bytes, count * 12 + 3, 4));// 四位代表该字段数据长度
					int startIndex = Integer.parseInt(getStr(bytes, count * 12 + 7, 5));// 五位代表数据截取的开始位置
					String result = "";
					
					String specialStr = getStr(bytes, dateLength + startIndex, 3);
					//判断是否有指示符
					if("".equals(specialStr.trim())){
						result = getStr(bytes, dateLength + startIndex, length);
					}else{
						result = getStr(bytes, dateLength + startIndex+3, length-3);
					}
					marc.setFlag(specialStr.replace(" ", "&nbsp;&nbsp;"));
					marc.setId(field);
					marc.setContent(result);
					list.add(marc);
					break;
				}
				count++;

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;

	}
    
    public static Set<String> getIsbnSet(String content){
    	
    	if (content == null || "".equals(content)){
    		return null;
    	}
    	
    	Set<String> isbns = new HashSet<>();
    	
    	Pattern p = Pattern.compile("a(\\d*[X]?)"); 
    	BookMarcDTO book ;
    	try {
     		String[] ss = content.split(String.valueOf(RECEND));

    		List<BookMarcDTO> bookList = null;
    		for (String s : ss){
				bookList = getMarcInfo(s);
				if (bookList != null && !bookList.isEmpty()) {
					
					book = bookList.get(0);
					if (StringUtils.isNotBlank(book.getContent())){
						book.setContent(book.getContent().replace("-",""));
						Matcher m = p.matcher(book.getContent());
						Boolean flag = m.find();
						if (flag) {
							if (m.group(0).length() >= 10){
								isbns.add(m.group(0).substring(1));
							}
						}else{
							logger.info("不符合isbn规范"+book.getContent());
						}
					}
				}
    			
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("文件中读取到"+ isbns.size() +"个ISBN："+isbns);
    	return isbns;
    }
    
    private static String getStr(byte[] bytes, int start, int end) {

		try {
			return new String(bytes, start, end, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    public static void main(String[] args){
//    	String content = "01257cam0 2200301   450 00500170000001000240001703500210004110000410006209900210010310100080012410200070013210500180013910600060015720001280016321000400029121500150033151700820034660600350042860600450046368600170050869000150052570100640054080100220060499700200062639600070064699901510065399901510080420160628161246.8  a489041360Xd1429円  a(Sirsi) a2850971  a19981215d1998    km y0jpny50      da  aCAL 0320160133850 ajpn  aJP  ay   z   000yy  ar1 a偉人たちのお脈拝見Aイジンタチ ノ オミャク ハイケンe英傑の死の謎にせまるf若林利光著  a東京c日本医療企画d1998.10  a269pd19cm1 a英傑の死の謎にせまるAエイケツ ノ シ ノ ナゾ ニ セマル0 a伝記Aデンキy日本2BSH0 a死亡原因Aシボウゲンイン2BSH  a281.042NDC9  aI313.65v5 0a若林利光Aワカバヤシ トシミツ3DA080820424著 0aJPbNIIc19981215  a外语大学馆  a16  a36/I313.65/767w中图法_Jc1iP120J000038428l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/4xIo.CIRCNOTE. 千叶赠书  a36/I313.65/767w中图法_Jc2iP120J000038429l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xIo.CIRCNOTE. 千叶赠书01351cam0 2200325   450 00500170000001000240001702000190004103500210006010000410008109900210012210100080014310200070015110500180015810600060017620000820018221000390026421500150030351700470031860600470036568600160041268600160042869000150044470100610045980100220052099700200054239600070056299901520056999901520072199901520087320160614104730.1  a4474070917d1500円  aJPbJP90045891  a(Sirsi) a2850976  a19910301d1990    km y0jpny50      da  aCAL 0320160114200 ajpn  aJP  ay   z   000yy  ar1 a石火の一刀Aセッカ ノ イットウe御子神典膳f松永義弘著  a東京c第一法規出版d1990.7  a299pd20cm1 a御子神典膳Aミコガミ テンゼン0 a日本文学Aニホンブンガク2NDLSH  a913.62NDC8  aKH3462NDLC  aI313.45v5 0a松永義弘Aマツナガ ヨシヒロ3DA040275084著 0aJPbNIIc19910301  a外语大学馆  a16  a36/I313.45/4181w中图法_Jc1iP120J000038430l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/4xIo.CIRCNOTE. 千叶赠书  a36/I313.45/4181w中图法_Jc2iP120J000038431l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/4xIo.CIRCNOTE. 千叶赠书  a36/I313.45/4181w中图法_Jc3iP120J000038557l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xIo.CIRCNOTE. 千叶赠书01538cam  2200349   450 00500170000001000240001703500210004110000410006209900210010310100080012410200070013210500180013910600060015720001680016321000340033121500150036530000250038030001650040551202240057060600300079460600450082460600380086968600180090769000150092570100430094070100550098370200850103880100220112399700200114539600070116599901500117299901500132220160707092428.8  a4921044643d1900円  a(Sirsi) a2854109  a20030908d2002    km y0jpny50      da  aCAL 0320160143150 ajpn  aJP  ay   z   000yy  ar1 a3万円から始めるわかる儲かる中国株A3マンエン カラ ハジメル ワカル モウカル チュウゴクカブf椋目孝治, 柏木理佳共著  a東京c全日出版d2002.12  a351pd21cm  a監修: 東洋証券  a表紙タイトル: 3万円から始めるわかる儲かる中国株 : 業界No.1による厳選30銘柄 / 椋目孝治,柏木理佳共著 ; 東洋証券監修1 a3万円から始めるわかる儲かる中国株A3マンエン カラ ハジメル ワカル モウカル チュウゴクカブe業界No.1による厳選30銘柄 / 椋目孝治,柏木理佳共著 ; 東洋証券監修0 a株式Aカブシキ2BSH0 a株式相場Aカブシキソウバ2BSH0 a企業Aキギョウy中国2BSH  a338.1552NDC9  aF830.91v5 0a椋目孝治Aムクメ コウジ4著 0a柏木理佳Aカシワギ リカ3DA136472304著 0a東洋証券株式会社Aトウヨウ ショウケン カブシキ ガイシャ 0aJPbNIIc20030908  a外语大学馆  a16  a36/F830.91/11w中图法_Jc1iP120J000038696l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xFo.CIRCNOTE. 千叶赠书  a36/F830.91/11w中图法_Jc2iP120J000038697l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/8xFo.CIRCNOTE. 千叶赠书02456cam  2200397   450 00500170000001000240001703500210004110000410006209900210010310100080012410200070013210500180013910600060015720001190016321000330028221500140031522500860032941000930041560600470050868600160055569000130057170100750058470200460065970200490070580100220075499700200077639600070079699901510080399901510095420160707143659.6  a4272360345d2500円  a(Sirsi) a2854163  a19990617d1999    km y0jpny50      da  aCALB0320160143650 ajpn  aJP  ay   z   000yy  ar1 a虫歯がいたい君にAムシバ ガ イタイ キミ ニf関口武三郎著g大野孝一, 細川留美子絵  a東京c大月書店d1999.4  a31pd27cm2 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv4 012001 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv40 a小児科学Aショウニカガク2NDLSH  a490.72NDC8  aTS976v5 0a関口武三郎,Aセキグチ ブサブロウf1943-3DA120708194著 0a大野孝一Aオオノ コウイチ4绘 0a細川留美子Aホソカワ ルミコ4绘 0aJPbNIIc19990618  a外语大学馆  a16  a36/TS976/27:4w中图法_Jc1iP120J000038711l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书  a36/TS976/27:4w中图法_Jc2iP120J000038712l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书01431cam  2200325   450 00500170000001000240001703500210004110000410006209900210010310100080012410200070013210500180013910600060015720001190016321000330028221500140031522500860032941000930041560600470050868600160055569000130057170100750058470200460065970200490070580100220075499700200077639600070079699901510080399901510095420160707143659.6  a4272360345d2500円  a(Sirsi) a2854163  a19990617d1999    km y0jpny50      da  aCALB0320160143650 ajpn  aJP  ay   z   000yy  ar1 a虫歯がいたい君にAムシバ ガ イタイ キミ ニf関口武三郎著g大野孝一, 細川留美子絵  a東京c大月書店d1999.4  a31pd27cm2 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv4 012001 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv40 a小児科学Aショウニカガク2NDLSH  a490.72NDC8  aTS976v5 0a関口武三郎,Aセキグチ ブサブロウf1943-3DA120708194著 0a大野孝一Aオオノ コウイチ4绘 0a細川留美子Aホソカワ ルミコ4绘 0aJPbNIIc19990618  a外语大学馆  a16  a36/TS976/27:4w中图法_Jc1iP120J000038711l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书  a36/TS976/27:4w中图法_Jc2iP120J000038712l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书01642cam  2200385   450 00500170000001000240001703500210004110000410006209900210010310100080012410200070013210500180013910600060015720001190016321000330028221500140031522500860032941000930041560600470050860600500055560600350060560600320064060600300067268600160070268600160071869000130073470100630074770200460081070200490085680100220090599700200092739600070094799901510095499901510110520160707144118.1  a4272360353d2500円  a(Sirsi) a2854165  a19990617d1999    km y0jpny50      da  aCAL 0320160143670 ajpn  aJP  ay   z   000yy  ar1 aケガをよくする君にAケガ オ ヨクスル キミ ニf織田英昭著g大野孝一, 細川留美子絵  a東京c大月書店d1999.4  a31pd27cm2 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv5 012001 a病気とケガのタネあかしAビョウキ ト ケガ ノ タネアカシv50 a小児科学Aショウニカガク2NDLSH0 a医学教育Aイガクキョウイク2NDLSH0 a外傷Aガイショウ2NDLSH0 a骨折Aコッセツ2NDLSH0 a病気Aビョウキ2BSH  a490.72NDC8  a493.92NDC9  aTS976v5 0a織田英昭,Aオダ ヒデアキf1929-3DA120708084著 0a大野孝一Aオオノ コウイチ4绘 0a細川留美子Aホソカワ ルミコ4绘 0aJPbNIIc20160128  a外语大学馆  a16  a36/TS976/27:5w中图法_Jc1iP120J000038713l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书  a36/TS976/27:5w中图法_Jc2iP120J000038714l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xTSo.CIRCNOTE. 千叶赠书01642cam  2200385   450 00500170000001000280001702000190004503500210006410000410008509900210012610100080014710200070015510500180016210600060018020002180018621000390040421500260044330000250046930000270049430001450052151701870066651701450085360600540099860600300105260600480108260600590113068600160118969000150120570100580122070100550127870200580133380100220139199700200141339600070143399901490144099901490158920160707164729.9  a4890414940d1600円+税  aJPbJP20265907  a(Sirsi) a2854198  a20020423d2002    km y0jpny50      da  aCAL 0320160144020 ajpn  aJP  ay   z   000yy  ar1 a聴覚障害学生サポートガイドブックAチョウカク ショウガイ ガクセイ サポート ガイドブックeともに学ぶための講義保障支援の進め方f白澤麻弓, 徳田克己著  a東京c日本医療企画d2002.4  ax, 151pc挿図d21cm  a監修: 斎藤佐和  a参考文献: p130-132  a異なりアクセスタイトル: 聴覚障害学生サポートガイドブック : ともに学ぶための講義保障支援の進め方1 a聴覚障害学生サポートガイドブックAチョウカク ショウガイ ガクセイ サポート ガイドブックeともに学ぶための講義保障支援の進め方1 aともに学ぶための講義保障支援の進め方Aトモ ニ マナブ タメ ノ コウギ ホショウ シエン ノ ススメカタ0 a聴覚障害Aチョウカクショウガイ2BSH0 a大学Aダイガク2BSH0 a高等学校Aコウトウガッコウ2BSH0 a聴力障害Aチョウリョクショウガイ2NDLSH  a378.22NDC9  aC913.69v5 0a白澤麻弓Aシラサワ マユミ3DA124714124著 0a徳田克己Aトクダ カツミ3DA026100794著 0a斎藤佐和,Aサイトウ サワf1943-3DA00468159 0aJPbNIIc20090330  a外语大学馆  a16  a36/C913.69/6w中图法_Jc1iP120J000038751l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xCo.CIRCNOTE. 千叶赠书  a36/C913.69/6w中图法_Jc2iP120J000038752l第二书库m外语大学馆pY20.00rYsYt日文普通书u2016/7/7xCo.CIRCNOTE. 千叶赠书";
		String bookFileName = "D:\\ftp\\waiyu20160915.iso";
//		String bookFileName = WaiYuLibrary.getBookFileName();
		String content = FileUtil.readString1(bookFileName);

    	Set<String> s = getIsbnSet(content);
    	/*for (String str : s){
    		System.out.println(str);
    	}*/
    }
}
