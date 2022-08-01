package org.ilmostro.redis.stack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.dengliming.redismodule.redisearch.RediSearch;
import io.github.dengliming.redismodule.redisearch.index.Document;
import io.github.dengliming.redismodule.redisearch.index.DocumentOptions;
import io.github.dengliming.redismodule.redisearch.index.RSLanguage;
import io.github.dengliming.redismodule.redisearch.index.schema.Field;
import io.github.dengliming.redismodule.redisearch.index.schema.FieldType;
import io.github.dengliming.redismodule.redisearch.index.schema.Schema;
import io.github.dengliming.redismodule.redisearch.index.schema.TextField;
import io.github.dengliming.redismodule.redisearch.search.NumericFilter;
import io.github.dengliming.redismodule.redisearch.search.SearchOptions;
import io.github.dengliming.redismodule.redisearch.search.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class RedisSearchClientTests {

	@Autowired
	private Redisson client;

	@Test
	void create(){
		final RediSearch search = new RediSearch(client.getCommandExecutor(), "test-search");
		search.createIndex(new Schema()
				.addField(new TextField("title"))
				.addField(new TextField("content"))
				.addField(new Field("age", FieldType.NUMERIC))
				.addField(new Field("location", FieldType.GEO)));
	}

	@Test
	void add(){
		final RediSearch search = new RediSearch(client.getCommandExecutor(), "test-search");
		Map<String, Object> fields = new HashMap<>();
		fields.put("title", "Hi");
		fields.put("content", "OOOO");
		search.addDocument(new Document("doc1", 1.0d, fields), new DocumentOptions());
	}

	@Test
	void add1(){
		final RediSearch search = new RediSearch(client.getCommandExecutor(), "test-search");
		Map<String, Object> fields = new HashMap<>();
		fields.put("age", 1);
		fields.put("title", "this is test title");
		search.addDocument(new Document("doc2", 1, fields), new DocumentOptions());
	}


	@Test
	void search_number(){
		final RediSearch search = new RediSearch(client.getCommandExecutor(), "test-search");
		// Search with NumericFilter
		SearchResult searchResult = search.search("title", new SearchOptions()
				.noStopwords()
				.language(RSLanguage.ENGLISH)
				.filter(new NumericFilter("age", 0, 4)));
		for (Document document : searchResult.getDocuments()) {
			for (Map.Entry<String, Object> entry : document.getFields().entrySet()) {
				log.info("field:[{}], value:[{}]", entry.getKey(), entry.getValue());
			}
		}
	}

	@Test
	void search_string(){
		final RediSearch search = new RediSearch(client.getCommandExecutor(), "test-search");
		// 这里的test代表的是文本里面的数字
		final SearchResult result = search.search("test", new SearchOptions()
				.noStopwords()
				.language(RSLanguage.ENGLISH)
		);
		for (Document document : result.getDocuments()) {
			for (Map.Entry<String, Object> entry : document.getFields().entrySet()) {
				log.info("field:[{}], value:[{}]", entry.getKey(), entry.getValue());
			}
		}
	}

}
