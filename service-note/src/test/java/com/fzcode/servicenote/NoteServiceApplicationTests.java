package com.fzcode.servicenote;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteServiceApplicationTests {
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//    @Test
//    void contextLoads() {
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        // 分页参数
//        Pageable pageable = PageRequest.of(0, 20);
//        // 高亮参数
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        String preTags = "<span class='red'>";
//        String postTags = "</span>";
//        highlightBuilder.preTags(preTags);
//        highlightBuilder.postTags(postTags);
//        highlightBuilder.field("title");
//        highlightBuilder.field("text");
//        // 搜索参数
//        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("恍惚","text","title").type(MultiMatchQueryBuilder.Type.MOST_FIELDS);
//        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder).withQuery(queryBuilder).withPageable(pageable).build();
//        System.out.println("开始查询");
//        SearchHits<Note> noteSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery,Note.class, IndexCoordinates.of("blog-note"));
//        noteSearchHits.stream().forEach(e->System.out.println(JSONUtils.stringify(e.getContent())));
//    }

}
