package com.cts.fsd.test.dao;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.fsd.test.configuration.TestDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDataSourceConfiguration.class })
@org.springframework.transaction.annotation.Transactional
public abstract class EntityDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void setUp() throws Exception {
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
		System.out.println("dbConn.getSchema(); " + dbConn.getSchema());
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
	}

	protected abstract IDataSet getDataSet() throws Exception;
}
