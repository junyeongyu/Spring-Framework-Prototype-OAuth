package com.junyeong.yu.prototype.boot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.junyeong.yu.prototype.boot.model.User;
import com.junyeong.yu.prototype.boot.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    private static final String KEY_NAME = "id";
    private static final Long READ_CAPACITY_UNITS = 5L;
    private static final Long WRITE_CAPACITY_UNITS = 5L;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void init() throws Exception {

        ListTablesResult listTablesResult = amazonDynamoDB.listTables();

        listTablesResult.getTableNames().stream().
                filter(tableName -> tableName.equals(User.TABLE_NAME)).forEach(tableName -> {
            amazonDynamoDB.deleteTable(tableName);
        });

        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(KEY_NAME).withAttributeType("S"));

        List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
        keySchemaElements.add(new KeySchemaElement().withAttributeName(KEY_NAME).withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(User.TABLE_NAME)
                .withKeySchema(keySchemaElements)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(READ_CAPACITY_UNITS)
                        .withWriteCapacityUnits(WRITE_CAPACITY_UNITS));

        amazonDynamoDB.createTable(request);
    }

    @Test
    public void sampleTestCase() {
        User dave = new User("Dave", "Matthews");
        repository.save(dave);

        User carter = new User("Carter", "Beauford");
        repository.save(carter);

        Page<User> result = repository.findByLastName("Matthews", new PageRequest(0, 10));
        List<User> userList = result.getContent();
        assertThat(userList.size(), is(1));
        assertThat(userList, hasItem(dave));
    }
}
