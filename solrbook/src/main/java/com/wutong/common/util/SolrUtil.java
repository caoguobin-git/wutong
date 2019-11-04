package com.wutong.common.util;//package com.wutong.common.util;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.net.URLDecoder;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.pdfbox.pdfparser.PDFParser;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.util.PDFTextStripper;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrQuery.ORDER;
//import org.apache.solr.client.solrj.SolrQuery.SortClause;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.client.solrj.response.TermsResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.SimpleBookmark;
//import com.thinkgem.jeesite.common.config.Global;
//import com.thinkgem.jeesite.common.mongo.MongoPage;
//import com.thinkgem.jeesite.common.utils.IdGen;
//import com.thinkgem.jeesite.common.utils.StringUtils;
//import com.thinkgem.jeesite.modules.bzzx.entity.PostInfo;
//import com.thinkgem.jeesite.modules.fastdfs.util.FastDfsUtil;
//import com.thinkgem.jeesite.modules.utils.CommonConstants;
//import com.thinkgem.jeesite.modules.utils.CommonUtils;
//import com.thinkgem.jeesite.modules.utils.RedisUtil;
//import com.thinkgem.jeesite.modules.utils.Resource;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@SuppressWarnings("deprecation")
//public class SolrUtil {
//    private static List<ChapterTree> listTree = null;
//    public SolrUtil(){
//
//    }
//
//    public static void main(String[] args) throws SolrServerException, IOException {
////        Map<String, String> map = new HashMap<>();
////        map.put("id", IdGen.uuid());
////        map.put("filePath", "D:/temp/15824_L.pdf");
////        detele("2a95116965f24e3f93cff6185f2c5dc2");
////        save(map);
////        PdfReader reader = new PdfReader("E:/图书/2533/2533/PDF/2533_h.pdf");
////        List list = SimpleBookmark.getBookmark(reader);
////        for (Iterator i = list.iterator(); i.hasNext();) {
////            showBookmark((Map) i.next(),"");
////        }
////
////        JSONArray jsonArray = JSONArray.fromObject(listTree);
////        System.out.println(jsonArray.toString());
//        //queryById("04844302a7404ca3ad0a313c2871167b");
//
//        /*List<TocInfo> listToc = new ArrayList<>();
//        JSONArray jsonList = JSONArray.fromObject("[{'id':'a147dfe2-40ad-4533-9b9b-5497e05f6bfd','pId':'0','title':'序','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AK_STAAANBF_XK_c01.html','name':'序','pagenum':'22'},{'id':'bcc88c52-d2d6-460d-b08c-6ee342ad2f18','pId':'0','title':'前言','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AZSdtAAAMt1l2a5444.html','name':'前言','pagenum':'24'},{'id':'439e0392-026d-4e14-993e-73d933118798','pId':'0','title':'第一章 普查组织与实施','htmlUrl':'','name':'第一章 普查组织与实施','pagenum':'28'},{'id':'50bc7208-8727-4680-bd2d-c7958dea3bf1','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第一节 编制方案','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AJegWAABc1VIRK9s76.html','name':'第一节 编制方案','pagenum':'28'},{'id':'82fab97c-7104-4e86-9863-63669a294127','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第二节 设立机构','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AHtDTAAAPNTNA-SE63.html','name':'第二节 设立机构','pagenum':'35'},{'id':'3bbf1039-5e68-4712-b8bc-e0a2330fc14b','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第三节 组织培训','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AAKksAAAfhd8tJiY38.html','name':'第三节 组织培训','pagenum':'36'},{'id':'6865d823-fe4e-41a7-a446-f3c8fc588b04','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第四节 宣传动员','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AE_HPAAAiK6oLt4s21.html','name':'第四节 宣传动员','pagenum':'39'},{'id':'8d2c89c4-bd45-44c9-aa7f-088c523161e1','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第五节 进度和质量控制','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-ANMBqAAAp9nrZUwQ03.html','name':'第五节 进度和质量控制','pagenum':'41'},{'id':'eac4d04b-d94c-4f89-b438-8d79656098a1','pId':'439e0392-026d-4e14-993e-73d933118798','title':'第六节 数据处理系统建设','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AZQtGAABcWvwBZCs33.html','name':'第六节 数据处理系统建设','pagenum':'44'},{'id':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','pId':'0','title':'第二章 安徽省第一次水利普查公报','htmlUrl':'','name':'第二章 安徽省第一次水利普查公报','pagenum':'54'},{'id':'832a6233-40a0-4a6e-95f1-788013600c63','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第一节 河湖基本情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AKA0KAAAC2rbh9WY43.html','name':'第一节 河湖基本情况','pagenum':'54'},{'id':'e2d761b9-4798-4bfc-a89b-b6ca3a019b80','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第二节 水利工程基本情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AKTW1AAAR5d_bHo024.html','name':'第二节 水利工程基本情况','pagenum':'54'},{'id':'5d84dc5f-c1f3-4ce3-b4e0-0eb9505bffe9','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第三节 经济社会用水情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AIT-vAAABOatcdHM56.html','name':'第三节 经济社会用水情况','pagenum':'57'},{'id':'8b0ec503-f265-4f02-a3e0-402cb3a6dcf5','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第四节 河湖开发治理情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AOeG8AAAENW1yh7865.html','name':'第四节 河湖开发治理情况','pagenum':'57'},{'id':'b3297b08-18a7-45ce-b8ef-77b2644f1422','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第五节 水土保持情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AfXV8AAABwYZCx6c69.html','name':'第五节 水土保持情况','pagenum':'58'},{'id':'84ff4578-4293-436e-a9a7-76be49400d8d','pId':'642afb96-cd24-4b03-ae98-5cfb22a29fbe','title':'第六节 水利行业能力建设情况','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-Ac2SJAAAI7-sc8EM55.html','name':'第六节 水利行业能力建设情况','pagenum':'58'},{'id':'3b8c5429-5edb-471d-9f7f-3850725f2e61','pId':'0','title':'第三章 典型经验交流','htmlUrl':'','name':'第三章 典型经验交流','pagenum':'59'},{'id':'51db41ad-0220-4044-8dca-886b67ec708d','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'踏波逐浪 把脉江河——安徽省第一次水利普查工作成效与经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AI_hyAABSvW_1UrA48.html','name':'踏波逐浪 把脉江河——安徽省第一次水利普查工作成效与经验','pagenum':'59'},{'id':'ea918bcf-158f-433d-ab0f-4c955c55a85f','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查质量控制措施与体会','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AbJvUAAAzEIEiyA485.html','name':'安徽省第一次水利普查质量控制措施与体会','pagenum':'65'},{'id':'4c77955e-ed8a-4935-8ffa-fc257bcd5c70','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查培训工作的基本经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-ACVGkAAAs-Uj1DWU70.html','name':'安徽省第一次水利普查培训工作的基本经验','pagenum':'69'},{'id':'f9311241-aeb4-465e-9dae-f62332f953b1','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'强措施 抓重点 保质量——合肥市第一次水利普查取得成效','htmlUrl':'group1/M00/00/1F/wKgBoFkVDH-AUYCEAAAwQ6NzE0s69.html','name':'强措施 抓重点 保质量——合肥市第一次水利普查取得成效','pagenum':'73'},{'id':'e6c52710-9cf2-45de-bd1c-65a32a741ddc','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'精心组织定方案 六项举措保进展——亳州市顺利完成第一次水利普查各项工作','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAD8jjAAAvD8Hm8gQ66.html','name':'精心组织定方案 六项举措保进展——亳州市顺利完成第一次水利普查各项工作','pagenum':'77'},{'id':'2afd6aa8-825a-4a58-a4ff-4c54216f8cf5','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'超前谋划抢先 集中会战攻关——滁州市第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAKsq9AAAo5Z0miiU63.html','name':'超前谋划抢先 集中会战攻关——滁州市第一次水利普查工作的主要经验','pagenum':'81'},{'id':'c4e6f437-aed0-49af-b4ed-6267a9d5f995','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'组织有序 逐级校验——芜湖市全面按时完成第一次水利普查任务','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICACpN6AAAYIaPmHVY62.html','name':'组织有序 逐级校验——芜湖市全面按时完成第一次水利普查任务','pagenum':'84'},{'id':'5eb04569-6937-4701-ab46-3108c7923c8a','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'强化技术指导 严格控制质量——黄山市第一次水利普查工作体系高效运转','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAUW14AAAw3j6XRpU30.html','name':'强化技术指导 严格控制质量——黄山市第一次水利普查工作体系高效运转','pagenum':'86'},{'id':'653d25d3-1640-4682-a0cc-dda7a2d5b56f','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'强化协调 强化指导 强化调度——宿州市第一次水利普查工作三项措施并举','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAenRWAAAiYR34pUQ02.html','name':'强化协调 强化指导 强化调度——宿州市第一次水利普查工作三项措施并举','pagenum':'90'},{'id':'ae5f22b9-1f54-4f46-99d6-cf52605ed511','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'精心组织 扎实推进——舒城县第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAEXQQAAAmQvpE--M81.html','name':'精心组织 扎实推进——舒城县第一次水利普查工作的主要经验','pagenum':'93'},{'id':'eaea4d9c-114c-4ee1-96a0-6d3803a061d7','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'政府有力推动 相关部门联动 水利部门主动——徽州区第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAFXKmAAAprJv-RR833.html','name':'政府有力推动 相关部门联动 水利部门主动——徽州区第一次水利普查工作的主要经验','pagenum':'96'},{'id':'2972fea7-44f5-4a4e-b8fb-5818f70417c4','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'两级试点实战 会议交流推动——和县第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAM_64AAAegFCPn6k57.html','name':'两级试点实战 会议交流推动——和县第一次水利普查工作的主要经验','pagenum':'99'},{'id':'edbbedbf-476e-4aa8-b914-4c85c2959275','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'组织领导 超前谋划 严控质量——埇桥区第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAN01MAAAhCaoXUp446.html','name':'组织领导 超前谋划 严控质量——埇桥区第一次水利普查工作的主要经验','pagenum':'102'},{'id':'d9d053d6-c207-4fd3-a791-19c5bd07de50','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'运筹帷幄统全局 苦练内功求质量——寿县第一次水利普查工作的主要经验','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAGSBuAAAU9oeXiwg45.html','name':'运筹帷幄统全局 苦练内功求质量——寿县第一次水利普查工作的主要经验','pagenum':'105'},{'id':'07790d7a-1c3c-4b90-aae2-70cffa9eee2c','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查资产情况初步分析','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAKhiGAAAbrBjY0qg38.html','name':'安徽省第一次水利普查资产情况初步分析','pagenum':'107'},{'id':'c629d74e-45d5-4be9-9e01-314d96ad1fea','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查河湖基本情况普查及成果效益展望','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAQeP6AAAuH5RbAuI87.html','name':'安徽省第一次水利普查河湖基本情况普查及成果效益展望','pagenum':'109'},{'id':'7b322db7-7a8c-4860-aeae-a85976c9a726','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查灌区专项普查数据审核工作综析','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAU2DUAAA0Ay6XJK067.html','name':'安徽省第一次水利普查灌区专项普查数据审核工作综析','pagenum':'113'},{'id':'4f504d82-9d9a-48a5-93cd-a67a9b029570','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查水库工程普查数据汇总审核与应用思考','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAfD3ZAAAtZi77_Nc09.html','name':'安徽省第一次水利普查水库工程普查数据汇总审核与应用思考','pagenum':'117'},{'id':'8543713e-68eb-4f24-8fcb-535116c2d631','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查农村供水工程普查数据审核工作的经验总结','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAaD5bAAAtwjIF8Fk00.html','name':'安徽省第一次水利普查农村供水工程普查数据审核工作的经验总结','pagenum':'121'},{'id':'78d08ea9-8199-4ddb-925e-e6a6829f55b5','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查地下水取水井专项普查的问题与对策','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAMiPqAAAkYKZ1J9M43.html','name':'安徽省第一次水利普查地下水取水井专项普查的问题与对策','pagenum':'125'},{'id':'7e37fc25-b0e6-4d63-8a24-7121282c547d','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查经济社会用水调查工作经验总结','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICARrSDAAAkXldilSI77.html','name':'安徽省第一次水利普查经济社会用水调查工作经验总结','pagenum':'128'},{'id':'1174be77-d262-4a11-bc5f-f5e3021d334a','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查河流开发治理保护普查工作的常见问题与对策','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICASs9gAAAbB_K1hVc28.html','name':'安徽省第一次水利普查河流开发治理保护普查工作的常见问题与对策','pagenum':'131'},{'id':'b07bca24-a401-42c7-9d78-935a1a4eb967','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查水力侵蚀普查工作的探讨','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAb9SqAAAyCFpOb3473.html','name':'安徽省第一次水利普查水力侵蚀普查工作的探讨','pagenum':'133'},{'id':'9f112670-0b52-4341-8faa-4ebb65118899','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'安徽省第一次水利普查水利行业能力建设情况普查数据审核工作探讨','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAMCYSAAAvlK_sF5w76.html','name':'安徽省第一次水利普查水利行业能力建设情况普查数据审核工作探讨','pagenum':'138'},{'id':'c9d54625-c2e5-41b0-8f4a-a0595f3acbfb','pId':'3b8c5429-5edb-471d-9f7f-3850725f2e61','title':'第四章 大事记','htmlUrl':'group1/M00/00/1F/wKgBoFkVDICAVLhoAACrQhlb53Y53.html','name':'第四章 大事记','pagenum':'142'}]");
//        listToc = JSONArray.toList(jsonList,TocInfo.class);
//        String htmlContent = "";
//        for(int i = 0; i < listToc.size(); i++){
//            if(!"".equals(listToc.get(i).getHtmlUrl())){
//                String htmlUrl = "http://192.168.1.160:8090/"  + listToc.get(i).getHtmlUrl();
//                htmlContent += HttpRequestUtil.httpRequest(htmlUrl);
//            }
//        }*/
//       /* Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "a");
////        map.put("text", "cde");
////        SolrUtil.save(map);
//
//
//        queryIdByInfo(map);*/
//        //BookInfo bookInfo = new BookInfo();
//
//        //System.out.println(JSONArray.fromObject(query(bookInfo, "bookCore")));
//
//
//
//
//        /*for (int i = 0; i < 15; i++) {
//            BookInfo bookInfo = new BookInfo();
//            bookInfo.setId(IdGen.uuid());
//            bookInfo.setName("中国");
//            save(bookInfo, "hotSearchCore");
//        }*/
//
//        //hotSearchYYWZ();
//        hotPost();
//    }
//
//    /**
//     * 上传图书到fast
//     * @param file
//     * @return
//     */
//    public static Map<String, String> upload(@RequestParam MultipartFile file){
//        Map<String, String> map = new HashMap<>();
//        String fastUrl = FastDfsUtil.upload(file);
//        map.put("fastUrl", fastUrl);
//
//        return map;
//    }
//    /**
//     * 保存图书信息到solr
//     * @param map
//     */
//    public static void save(Map<String, String> map, String coreName){
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        try {
//            solr.setConnectionTimeout(3000);
//            SolrInputDocument doc = new SolrInputDocument();
////            doc.setField("id", map.get("id"));
////            doc.setField("filePath", map.get("filePath"));
////            doc.setField("text", map.get("text"));
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                doc.setField(entry.getKey(), entry.getValue());
//            }
//            solr.add(doc);
//            /**
//             * 执行optimize操作后solr会进行索引数据的合并来优化查询性能，如果索引数据很大，optimize则会花费很多的时间，
//             * 如果你在批量提交文档后每次都进行optimize操作，无疑提交速度会越来越慢，最后甚至导致solr不再响应你的提交请求。
//             * 另外，如果你的服务是主从模式，那么对master的optimize还会导致整个索引块同步到replica上，影响就更加可想而知了。
//             * optimize操作可以优化查询性能，所以还是有用的，可以在服务不忙的时候在solr自带的控制台手动执行optimize操作，也可以写个定时任务来完成。
//             *
//             * 为解决内存溢出和存储时慢的问题，注释掉optimize();
//             */
////            solr.optimize();
//            solr.commit();
//            solr.close();
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            try {
//                solr.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//    /**
//     * 获取pdf菜单
//     * @param file
//     * @param path
//     * @return
//     */
//    public static Map<String, String> getPdfInfo(@RequestParam MultipartFile file, String path){
//        Map<String, String> map = new HashMap<>();
//        try {
//            String tempUrl = FastDfsUtil.saveFileToService(file.getBytes(), path);
//            map = getPdfInfo(tempUrl);
//            map.put("chapter", getChapterTree(tempUrl));
//            FastDfsUtil.deleteTempFile(tempUrl);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return map;
//    }
//
//    public static <T> void save(T t, String coreName){
//        Class<? extends Object> obj = t.getClass();
//        Field[] fields = obj.getDeclaredFields();
//        Field.setAccessible(fields,true);
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        //HttpSolrServer solr = new HttpSolrServer("http://192.168.1.120:8080/solr/hotSearchCore");
//        solr.setConnectionTimeout(3000);
//        SolrInputDocument doc = new SolrInputDocument();
//        for (Field field : fields) {
//            try {
//                if (field.get(t) != null) {
//                    String key = field.getName().toString();
//                    Object value = field.get(t);
//                    /*if(field.getType().getName().equals("java.util.Date")){
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(value);
//                        value = sdf.format(date);
//                    }*/
//                    doc.setField(key, value);
//                }
//            } catch (IllegalArgumentException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        try {
//            solr.add(doc);
//            /**
//             * 执行optimize操作后solr会进行索引数据的合并来优化查询性能，如果索引数据很大，optimize则会花费很多的时间，
//             * 如果你在批量提交文档后每次都进行optimize操作，无疑提交速度会越来越慢，最后甚至导致solr不再响应你的提交请求。
//             * 另外，如果你的服务是主从模式，那么对master的optimize还会导致整个索引块同步到replica上，影响就更加可想而知了。
//             * optimize操作可以优化查询性能，所以还是有用的，可以在服务不忙的时候在solr自带的控制台手动执行optimize操作，也可以写个定时任务来完成。
//             *
//             * 为解决内存溢出和存储时慢的问题，注释掉optimize();
//             */
////            solr.optimize();
//            solr.commit();
//            solr.close();
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 根据字段和内容查询出id
//     * @param FieldName param
//     */
//    public static void queryIdByInfo(Map<String ,String> map, String coreName){
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        try {
//            solr.setConnectionTimeout(3000);
//            SolrQuery filterQuery = new SolrQuery();
//            StringBuilder params = new StringBuilder();
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                params.append(" AND " + entry.getKey() + ":" + entry.getValue() );
//            }
//            filterQuery.setQuery(params.toString());
//            QueryResponse query = solr.query(filterQuery);
//            SolrDocumentList restList = query.getResults();
//            for(SolrDocument solrDocument : restList){
//                solrDocument.get("id");
//                /*Collection<String> fieldNames = solrDocument.getFieldNames();
//                System.out.println(fieldNames.size());
//                for(String field : fieldNames){
//                    System.out.println(solrDocument.get(field));
//                }*/
//            }
//        } catch (SolrServerException | IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            try {
//                solr.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    /**
//     * 更新solr图书信息
//     * @param map
//     */
//    public static void updata(Map<String, String> map, String coreName){
//        String id = map.get("id");
//        detele(id, coreName);
//        save(map, coreName);
//    }
//
//    /**
//     * 根据id删除solr
//     * @param id
//     */
//    public static void detele(String id, String coreName){
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        try {
//            solr.setConnectionTimeout(3000);
//            solr.deleteByQuery("id:" + id);
//            solr.commit();
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            try {
//                solr.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//    /**
//     * 根据字段名和字段值删除solr数据
//     * @param key
//     * @param value
//     * @param coreName
//     */
//    public static void detele(String key, String value, String coreName){
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        //HttpSolrServer solr = new HttpSolrServer("http://192.168.1.120:8080/solr/" + coreName);
//        try {
//            solr.setConnectionTimeout(3000);
//            solr.deleteByQuery(key + ":" + value);
//            solr.commit();
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            try {
//                solr.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//    /**
//     * 根据pdf文件获取文本内容,目录
//     * @param pdfUrl
//     * @return
//     */
//    public static Map<String, String> getPdfInfo(String pdfUrl) {
//        FileInputStream infile = null;
//        Map<String, String> map = new HashMap<>();
//        try {
//            infile = new FileInputStream(pdfUrl);// 创建一个文件输入流
//            // 新建一个PDF解析器对象
//            PDFParser parser = new PDFParser(infile);
//            // 对PDF文件进行解析
//            parser.parse();
//            // 获取解析后得到的PDF文档对象
//            PDDocument pdfdocument = parser.getPDDocument();
//            // 新建一个PDF文本剥离器
//            PDFTextStripper stripper = new PDFTextStripper();
//            // 从PDF文档对象中剥离文本
//            String text = stripper.getText(pdfdocument);
//            map.put("text", text);
//            map.put("wordCount", text.length() + "");
//            map.put("pageCount", pdfdocument.getNumberOfPages() + "");
//            pdfdocument.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (infile != null) {
//                try {
//                    infile.close();
//                } catch (IOException e1) {
//                }
//            }
//
//        }
//        return map;
//    }
//    /**
//     * 获取pdf图书目录并转成树形结构
//     * @param path
//     * @return
//     */
//    @SuppressWarnings("rawtypes")
//	public static String getChapterTree(String path){
//        PdfReader reader;
//        listTree = new ArrayList<>();
//        try {
//            reader = new PdfReader(path);
//            List list = SimpleBookmark.getBookmark(reader);
//            for (Iterator i = list.iterator(); i.hasNext();) {
//                showBookmark((Map) i.next(),"");
//            }
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        JSONArray jsonArray = JSONArray.fromObject(listTree);
//        return jsonArray.toString();
//    }
//
//    @SuppressWarnings({ "rawtypes", "unused" })
//	private static void showBookmark(Map bookmark, String pid) {
//        ChapterTree tree = new ChapterTree();
//        String id = IdGen.uuid();
//        String name = bookmark.get("Title").toString();
//        tree.setId(id);
//        tree.setName(name);
//        tree.setPid(pid);
//        String page = bookmark.get("Page").toString().substring(0, bookmark.get("Page").toString().indexOf(" "));
//        tree.setPage(page);
//        listTree.add(tree);
//        JSONArray jsonArray = JSONArray.fromObject(listTree.get(listTree.size()-1));
//        ArrayList kids = (ArrayList) bookmark.get("Kids");
//        if (kids == null){
//            return;
//        }
//        for (Iterator i = kids.iterator(); i.hasNext();) {
//
//            showBookmark((Map) i.next(), 1);
//        }
//    }
//    @SuppressWarnings({ "rawtypes", "unchecked", "resource" })
//	public static <T> Resource query(MongoPage<T> t, String coreName){
//        Resource resource = new Resource();
//        Class<? extends Object> obj = t.getClass();
//        Field[] fields = obj.getDeclaredFields();
//        Field.setAccessible(fields, true);
//        HttpSolrServer solr = new HttpSolrServer(Global.getConfig("solrPath") + coreName);
//        //HttpSolrServer solr = new HttpSolrServer("http://192.168.1.120:8092/solr/" + coreName);
//        solr.setConnectionTimeout(3000);
//        SolrQuery solrQuery = new SolrQuery();
//        StringBuilder params = new StringBuilder();
//        boolean isNumOne = true;
//        List<String> list = Arrays.asList(t.getList());
//        String rtParam =  t.getRtParam();
//        Map<String, String> qMap = new HashMap<>();//q查询Map
//        Map<String, String> fqMap = new HashMap<>();//fq查询Map
//        String fl = "";
//        for (Field field : fields) {
//            fl += field.getName().toString() + ",";
//            try {
//                solrQuery.addHighlightField(field.getName().toString());// 高亮字段
//                if (field.get(t) != null) {
//                    String key = field.getName().toString();
//                    String value = field.get(t).toString();
//
//                    if(field.getType().getName().indexOf("int")==-1 && field.getType().getName().indexOf("double")==-1){
//                        if(isNumOne){
//                            try {
//                                value = URLDecoder.decode(value, "UTF-8");
//                            	if(list.contains(key)){
//                                    fqMap.put(key, value);
//                                    continue;
//                                }else if(StringUtils.isNotEmpty(t.getWd())){
//                                    continue;
//                                }else{
//                                    qMap.put(key, value);
//                                }
//                                params.append(key  + ":" + value);
//                            } catch (UnsupportedEncodingException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                            isNumOne = false;
//                        }else{
//                            value = URLDecoder.decode(value, "UTF-8");
//                            if(list.contains(key)){
//                                fqMap.put(key, value);
//                                continue;
//                            }else if(StringUtils.isNotEmpty(t.getWd())){
//                                continue;
//                            }else{
//                                qMap.put(key, value);
//                            }
//                            params.append(" OR " + key  + ":" + value);
//                        }
//                    }
//                }
//            } catch (IllegalArgumentException | IllegalAccessException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        if(isNumOne){
//            params.append("*:*");
//        }
//
//        //wd全部字段检索
//        if(StringUtils.isNotEmpty(t.getWd())){
//            try {
//                params = new StringBuilder(URLDecoder.decode(t.getWd(), "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        if("true".equals(t.getIsDefType())){
//            solrQuery.set("defType","dismax");
//        }
////        solrQuery.setHighlight(true).setHighlightSimplePre("<font style='color: red;background: #ff0'>").setHighlightSimplePost("</font>");
//        solrQuery.setHighlight(true).setHighlightSimplePre("<em>").setHighlightSimplePost("</em>");
//        solrQuery.setQuery(params.toString());
//        solrQuery.setStart(t.getPageIndex());
//        solrQuery.setRows(t.getPageSize());//每一页多少值
//        solrQuery.setHighlightFragsize(t.getFragSize());//分片大小
//        solrQuery.setHighlightSnippets(1);
//        solrQuery.setRequestHandler("/select");
//
//        //solrQuery.set("fl", "*,score");
//        //solrQuery.set("fl", "id,bookId,resourceUrl,imageUrl,typeId,price,name,author,descripe,isbn,keyword,pageCount,publisher,typeName,language,cnBookTypeId,cnBookTypeName,score");
//        if(StringUtils.isEmpty(rtParam)){
//            //if("com.thinkgem.jeesite.modules.operateweb.entity.BookInfo".equals(t.getClass().getName())){
//                fl = fl.replaceAll("text,", "");
//            //}
//        }else{
//            fl = rtParam;
//        }
//        solrQuery.set("fl", fl + "score");
//        String fqStr = "";
//
//        Map<String, String> tmpMap = new HashMap<>();
//        tmpMap = fqMap;
//        fqMap = new HashMap<>();
//        //去除fq查询Map的特殊字符
//        for (String key : tmpMap.keySet()) {
//            String v = tmpMap.get(key);
//            v = v.replaceAll("\\*", "");
//            //v = escapeQueryChars(v);
//            //v = v.replaceAll(" ", "?");
//            fqMap.put(key, v);
//        }
//
//        //去除q查询Map的特殊字符
//        Map<String, String> tmpQMap = new HashMap<>();
//        tmpQMap = qMap;
//        for (String key : tmpQMap.keySet()) {
//            String v = tmpQMap.get(key);
//            v = v.replaceAll("\\*", "");
//            //v = escapeQueryChars(v);
//            //v = v.replaceAll(" ", "?");
//            qMap.put(key, v);
//        }
//        //拼接fq字符串
//        for (String key : fqMap.keySet()) {
//            String v = fqMap.get(key);
//            v = v.replaceAll("\\*", "");
//            if(StringUtils.isNotEmpty(v)){
//                fqStr += " " + key + ":" + v + " " + t.getFqOperationType();
//            }
//        }
//        //去掉最后的拼接条件
//        if (StringUtils.isNotEmpty(fqStr)) {
//        	fqStr = fqStr.substring(0, fqStr.length() - 3);
//        	fqStr = fqStr.trim();
//        	solrQuery.setFilterQueries(fqStr);
//		}
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat solrSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        String strDateString = "1970-00-00 ";
//        if(StringUtils.isEmpty(t.getDateParam())){
//            if(StringUtils.isNotEmpty(t.getDateFieldName())){
//                if (t.getStartDate() == null) {
//                    try {
//                        t.setStartDate(sdf.parse(strDateString));
//                    } catch (ParseException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//                if(t.getEndDate() == null){
//                    t.setEndDate(new Date());
//                }
//                solrQuery.setFilterQueries(t.getDateFieldName() + ":["+solrSdf.format(t.getStartDate())+" TO "+solrSdf.format(t.getEndDate())+"]");
//            }
//        }else{
//            String[] dateParm = t.getDateParam().split(",");
//            String dateList = null;
//            try {
//                dateList = URLDecoder.decode(t.getDateList(), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            JSONObject jsonDateObj = JSONObject.fromObject(dateList);
//            for (int i = 0; i < dateParm.length; i++) {
//                JSONObject jsonDate = JSONObject.fromObject(jsonDateObj.get(dateParm[i]));
//                Date startDate = null;
//                Date endDate = null;
//                try {
//                    startDate = sdf.parse(jsonDate.get("startDate") + "");
//                    endDate = sdf.parse(jsonDate.get("endDate") + "");
//                    if(startDate == null){
//                        startDate = sdf.parse(strDateString);
//                    }
//                } catch (ParseException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//                if(endDate == null){
//                    endDate = new Date();
//                }
//                solrQuery.setFilterQueries(dateParm[i] + ":["+solrSdf.format(startDate)+" TO "+solrSdf.format(endDate)+"]");
//            }
//        }
//        /*solrQuery.set("qf","name^10 descripe^20 text ^20");*/
//        solrQuery.addSort(new SortClause("score", ORDER.desc)); // 排序
//        if(StringUtils.isNotEmpty(t.getSortFieldName())){
//            solrQuery.addSort(new SortClause(t.getSortFieldName(), t.getSortType())); // 排序
//        }
//        /*solrQuery.setFacet(true).setFacetMinCount(1).setFacetLimit(5)// 段
//        .addFacetField("name").addFacetField("text").addFacetField("descripe");// 分片字段
//         */
//        QueryResponse queryResponse = null;
//        List<Map<String, Object>> listMap = new ArrayList();
//        Map<String, Object> map = null;
//        try {
//            queryResponse = solr.query(solrQuery);
//            SolrDocumentList solrDocumentList = queryResponse.getResults();
//            resource.setCount(solrDocumentList.getNumFound());
//
//            for(int i = 0;i < solrDocumentList.size(); i++){
//                String id = (String) solrDocumentList.get(i).getFieldValue("id");
//                map = new LinkedHashMap<String, Object>();
//                for (Field field : fields) {
//                    String key = field.getName().toString();
//                    if(queryResponse.getHighlighting().get(id).get(key) != null){
//                        String hlValue = queryResponse.getHighlighting().get(id).get(key).get(0);
//                        hlValue = hlValue.replaceAll("&amp;", "&");
//                        hlValue = StringEscapeUtils.unescapeHtml(hlValue);
//                        map.put(key, hlValue);
//                    }else{
//                        if(solrDocumentList.get(i).get(key) == null){
//                            map.put(key, "");
//                        }else{
//                            String value = "";
//                            if(field.getType().getName().equals("java.util.Date")){
//                                try {
//                                    value = sdfAll.format(solrDocumentList.get(i).get(key));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    // TODO: handle exception
//                                }
//                            }else{
//                                value = solrDocumentList.get(i).get(key).toString();
//                                value = value.replaceAll("&amp;", "&");
//                                value = StringEscapeUtils.unescapeHtml(value);
//                                if(StringUtils.isNotEmpty(fqMap.get(key))){
//                                    value = value.replaceAll(fqMap.get(key), "<em>" + fqMap.get(key) + "</em>");
//                                    value = stringProcess(value);
//                                }else if(StringUtils.isNotEmpty(qMap.get(key))){
//                                    value = value.replaceAll(qMap.get(key), "<em>" + qMap.get(key) + "</em>");
//                                    value = stringProcess(value);
//                                }
//                            }
//                            map.put(key, value);
//                        }
//                    }
//                }
//                listMap.add(map);
//            }
//        } catch (SolrServerException | IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        JSONArray json = JSONArray.fromObject(listMap);
//        int last = 1;//总页数
//        int count = (int) Long.parseLong(resource.getCount() + "");//总条数
//        int pageSize = t.getPageSize();//每页条数
//        last = (count + (pageSize - 1))/pageSize;
//        resource.setData(json);
//        resource.setPageNo(t.getPageIndex());
//        resource.setPageSize(t.getPageSize());
//        resource.setLast(last);
//        return resource;
//    }
//    public static String escapeQueryChars(String s) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//          char c = s.charAt(i);
//          // These characters are part of the query syntax and must be escaped
//          if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
//            || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
//            || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
//            || Character.isWhitespace(c)) {
//            sb.append('\\');
//          }
//          sb.append(c);
//        }
//        return sb.toString();
//    }
//    /**
//     * 热门搜索统计，保存到redis
//     */
//    @SuppressWarnings("resource")
//	public static void hotSearchYYWZ(){
//        Resource resource = new Resource();
//        HttpSolrServer solrserver = new HttpSolrServer(Global.getConfig("solrPath") + "hotSearchCore");
//        // 创建查询参数以及设定的查询参数
//        SolrQuery params = new SolrQuery();
//        params.set("q", "*:*");
//        params.set("qt", "/terms");
//
//        // parameters settings for terms requesthandler
//        // 参考（refer to）http://wiki.apache.org/solr/termscomponent
//        params.set("terms", "true");
//        params.set("terms.fl", "name");
//
//        //指定下限
////      params.set("terms.lower", ""); // term lower bounder开始的字符
////      params.set("terms.lower.incl", "true");
////      params.set("terms.mincount", "1");
////      params.set("terms.maxcount", "100");
//
////       http://localhost:8983/solr/terms?terms.fl=text&terms.prefix=学 //
////       using for auto-completing   //自动完成
////      params.set("terms.prefix", "联");
//         //params.set("terms.regex", "*.*");
//         params.set("terms.regex.flag", "case_insensitive");
////
//         params.set("terms.limit", "5");
////       params.set("terms.upper", ""); //结束的字符
////       params.set("terms.upper.incl", "false");
////
////       params.set("terms.raw", "true");
//
//        params.set("terms.sort", "count");//terms.sort={count|index} -如果count，各种各样的条款术语的频率（最高计数第一）。 如果index，索引顺序返回条款。默认是count
//
//        try {
//            // 查询并获取相应的结果！
//            QueryResponse response = solrserver.query(params);
//         // 获取相关的查询结果
//            if (response != null) {
//                TermsResponse termsResponse = response.getTermsResponse();
//                if (termsResponse != null) {
//                    Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();
//                    JSONArray jsonList = new JSONArray();
//                    for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {
//                        List<TermsResponse.Term> termList = termsEntry.getValue();
//                        JSONObject jsonObj = null;
//                        for (TermsResponse.Term term : termList) {
//                            jsonObj = new JSONObject();
//                            jsonObj.put("content", term.getTerm());
//                            jsonList.add(jsonObj);
//                        }
//                    }
//                    resource.setData(jsonList);
//                    RedisUtil.setRedisInfo("indexHotSearchYYWZ", JSONArray.fromObject(resource).toString(), -1);
//                }
//
//            }
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 标准在线热门帖子统计，保存到redis
//     */
//    @SuppressWarnings({ "resource", "unused", "unchecked" })
//    public static void hotPost(){
//        Resource resource = new Resource();
//        HttpSolrServer solrserver = new HttpSolrServer(Global.getConfig("solrPath") + "hotPostCore");
//        //HttpSolrServer solrserver = new HttpSolrServer("http://192.168.1.120:8092/solr/" + "hotPostCore");
//        // 创建查询参数以及设定的查询参数
//        SolrQuery params = new SolrQuery();
//        params.set("q", "*:*");
//        params.set("qt", "/terms");
//
//        // parameters settings for terms requesthandler
//        // 参考（refer to）http://wiki.apache.org/solr/termscomponent
//        params.set("terms", "true");
//        params.set("terms.fl", "postId");
//        //指定下限
////      params.set("terms.lower", ""); // term lower bounder开始的字符
////      params.set("terms.lower.incl", "true");
////      params.set("terms.mincount", "1");
////      params.set("terms.maxcount", "100");
//
////       http://localhost:8983/solr/terms?terms.fl=text&terms.prefix=学 //
////       using for auto-completing   //自动完成
////      params.set("terms.prefix", "联");
//         //params.set("terms.regex", "*.*");
//         params.set("terms.regex.flag", "case_insensitive");
////
//         params.set("terms.limit", "10");
////       params.set("terms.upper", ""); //结束的字符
////       params.set("terms.upper.incl", "false");
////
////       params.set("terms.raw", "true");
//
//        params.set("terms.sort", "count");//terms.sort={count|index} -如果count，各种各样的条款术语的频率（最高计数第一）。 如果index，索引顺序返回条款。默认是count
//
//        try {
//            // 查询并获取相应的结果！
//            QueryResponse response = solrserver.query(params);
//         // 获取相关的查询结果
//            if (response != null) {
//                TermsResponse termsResponse = response.getTermsResponse();
//                SolrDocumentList solrDocumentList = response.getResults();
//                if (termsResponse != null) {
//                    Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();
//                    JSONArray jsonList = new JSONArray();
//                    for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {
//                        List<TermsResponse.Term> termList = termsEntry.getValue();
//                        JSONObject jsonObj = null;
//                        PostInfo postInfo = null;
//                        Resource re = null;
//                        JSONArray jsonPostList = null;
//                        for (TermsResponse.Term term : termList) {
//                            jsonObj = new JSONObject();
//                            postInfo = new PostInfo();
//                            re = new Resource();
//                            jsonPostList = new JSONArray();
//                            postInfo.setPageIndex(0);
//                            postInfo.setPageSize(1);
//                            postInfo.setId("\"" + term.getTerm() + "\"");
//                            re = query(postInfo, CommonConstants.POST_CORE);
//                            jsonPostList = JSONArray.fromObject(re.getData());
//                            if(jsonPostList.size() > 0){
//                                jsonObj.put("id", CommonUtils.delHTMLTag(JSONObject.fromObject(jsonPostList.get(0)).getString("id")));
//                                jsonObj.put("name", JSONObject.fromObject(jsonPostList.get(0)).getString("title"));
//                                jsonObj.put("num", term.getFrequency());
//                                jsonList.add(jsonObj);
//                            }
//                        }
//                    }
//                    resource.setData(jsonList);
//                    RedisUtil.setRedisInfo("hotPost", JSONArray.fromObject(resource).toString(), -1);
//                }
//
//            }
//        } catch (SolrServerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    @SuppressWarnings("rawtypes")
//	private static void showBookmark(Map bookmark, int pid) {
//
//        ChapterTree tree = new ChapterTree();
//        String name = bookmark.get("Title").toString();
//        String id = IdGen.uuid();
//        tree.setId(id);
//        tree.setName(name);
//        String page = bookmark.get("Page").toString().substring(0, bookmark.get("Page").toString().indexOf(" "));
//        tree.setPage(page);
//        listTree.add(tree);
//        ArrayList kids = (ArrayList) bookmark.get("Kids");
//        if (kids == null){
//            return;
//        }
//        for (Iterator i = kids.iterator(); i.hasNext();) {
//            showBookmark((Map) i.next(), id);
//        }
//    }
//
//    @SuppressWarnings("unused")
//	private static String reText(String str){
//        str = str.replaceAll("<em>", "<font style='color: red;background: #ff0'>");
//        str = str.replaceAll("</em>", "</font>");
//        return str;
//    }
//    @SuppressWarnings("unused")
//	private static String stringProcess(String str){
//        int sumlength = 300;
//
//        String result = str;
//
//        Map<String, String> hm = new LinkedHashMap<String, String>();
//
//        String pat = "<em>[a-zA-Z0-9\u4E00-\u9FA5]*</em>";
//
//        Pattern pattern = Pattern.compile(pat);
//        Matcher matcher = pattern.matcher(str);
//
//        int i=0;
//
//        while(matcher.find()) {
//
//            hm.put("key_count_"+i+"◎", matcher.group());
//            result = result.replaceFirst(matcher.group(), "key_count_"+i+"◎");
//            i++;
//        }
//
//
//
//        String keyword="。";
//        if (result.indexOf(keyword)==-1){
//
//            if (result.indexOf(".")!=-1){
//                keyword=".";
//            }else{
//                keyword=" ";
//            }
//        }
//        String[] split = result.split("\\"+keyword);
//
//        StringBuilder sb = new StringBuilder();
//
//        Boolean breakFlag=false;
//        int j = 0;
//        for (String item : split) {
//            /*if(j==0 && split.length > 1){
//                j++;
//                continue;
//            }*/
//
//            if(item.indexOf("◎") == -1){
//                if (sb.length()<sumlength && sb.indexOf("<em>")!=-1){
//                    sb.append(item);
//                }
//
//                continue;
//            }
//            String tempString="";
//            if (item.length()>sumlength){
//                int position=item.indexOf("◎")+1;
//                String tempSub = item.substring(0,position);
//                tempString = tempSub + "......";
//                breakFlag=true;
//            }else{
//                tempString=item+keyword;
//            }
//            for (Map.Entry<String, String> entry : hm.entrySet()) {
//                if (tempString.indexOf(entry.getKey())!=-1){
//                    tempString = tempString.replaceFirst(entry.getKey(), entry.getValue());
//                }
//            }
//            sb.append(tempString);
//            if (breakFlag){
//                break;
//            }
//        }
//        if(StringUtils.isNotEmpty(sb.toString())){
//            return sb.toString();
//        }else{
//            return str;
//        }
//    }
//}
