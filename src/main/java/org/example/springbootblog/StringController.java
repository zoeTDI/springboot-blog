package org.example.springbootblog;

import note.NoteCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import readFile.ReadMD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 18324
 */
@RestController
public class StringController {
    @GetMapping("/api/getString")
    public String getString(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return "# Hello " + name + ", ***SpringBoot***.";
        }else {
            return "# Hello ***SpringBoot***.";
        }
    }
    @GetMapping("/api/getContent")
    public String getContent() {
        return "demo  \ndemo\n\n# Header 1\n\n## Header 2\n\n### Header 3\n\n#### Header 4\n\n##### Header 5\n\n###### Header 6\n\n**粗体**\n\n*斜体*\n\n***粗体和斜体***\n\n[链接](www.baidu.com)\n\n行内 `代码` 段\n\n```java\n\npublic class HelloWorld {\n  public static void main(String[] args) {\n    System.out.println(\"Hello World!\");\n  }\n}\n```\n\n> 引用\n\n> 多行引用\n> \n> 多行引用\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n![图片](1731198840342.png)";
    }
    @GetMapping("/api/connect/getContent")
    public String connectGetContent() {
        return """
                大家好，我是 Caldm，一名计算机信息系的大学生。我热爱技术，也热爱文学。在数字与文字的世界里，我寻找着平衡与和谐。我的性格比较安静，喜欢安静的环境，这让我能够更专注于编程和写作。

                **座右铭**：“路虽远，行则将至；事虽难，做则可成。”

                ### 爱好/兴趣

                - **阅读**：无论是网络文学还是传统文学作品，我都能沉浸其中，享受阅读的乐趣。
                - **写作**： 我喜欢用文字记录生活，表达思想，写作是我与世界沟通的方式之一。
                - **编程**： 我对编程充满热情，掌握 C/C++, Python, Java, HTML, CSS, JS 等多种编程语言。
                - **写日记**： 写日记是我记录日常和自我反思的习惯。
                ### 经历

                - **游戏玩家**： 我是《原神》的开服玩家，也是《明日方舟》的老玩家，虽然自称“咸鱼刀客塔”，但我依然享受游戏带来的乐趣。
                - **工程师**： 在《异星工厂》中，我拥有2000+小时的生产线工程师经验。
                - **技能大赛**： 我曾参加全省技能大赛，赛项为网站技术，这是对我的技术能力的一次重要检验。
                ### 建立网站的目的

                我建立这个网站的初衷是希望通过它输出我所学的知识，帮助那些需要的人。我相信分享知识是一种力量，能够让我们共同进步。

                ### 个人愿景 / 目标

                我的目标是毕业后成为一名前端程序员，或者成为一名网文作者。我相信通过不懈的努力和持续的学习，我能够实现我的梦想。

                联系方式见 [隐私政策](https://caldm.cn/index.php/privacy-policy/)

                """;
    }

    //    @GetMapping("/api/note/getNoteCard")
//    public NoteCard getNote() {
//        NoteCard noteCard = new NoteCard();
//        noteCard.setId(1);
//        noteCard.setTitle("Sample NoteCard");
//        noteCard.setPic("https://ob-tc-caldm-1315806820.cos.ap-nanjing.myqcloud.com/img/20241126211956.png");
//        noteCard.setCategory("Technology");
//        noteCard.setSummary("This is a sample noteCard content.");
//        noteCard.setDate("20231001121212");
//        noteCard.setAuthor("John Doe");
//        noteCard.addTags("Java");
//        noteCard.addTags("Spring Boot");
//        noteCard.addTags("API");
//        return noteCard;
//    }
//

    @GetMapping("/api/note/getNoteCard")
    public Map<String, Object> getNote() {
        Map<String, Object> yaml = new HashMap<>();
        try {
            ReadMD readMd = new ReadMD("D:\\Desktop\\test.md");
            yaml = readMd.getYaml();
            String content = readMd.getContent();
//            System.out.println(yaml);
//            System.out.println(content);
        } catch (IOException ioException) {
            System.out.println("error: " + ioException);
        }
        return yaml;
    }
}
