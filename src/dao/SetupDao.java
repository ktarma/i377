package dao;

import java.io.File;
import java.sql.SQLException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

public class SetupDao extends AbstractDao {

	public void createSchema() {
		System.out.println("tableExists:" + tableExists());
		if (!tableExists())
			executeSqlFromFile(getClassPathFile("schema.sql"));
	}

	private boolean tableExists() {
		try {
			st = getConnection().createStatement();
			rs = st.executeQuery("SELECT * FROM unit");
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
		return false;
	}

	public void insertDefaultData() {
		executeSqlFromFile(getClassPathFile("defaultdata.sql"));
	}

	private String getClassPathFile(String fileName) {
		return getClass().getClassLoader().getResource(fileName).getFile();
	}

	private void executeSqlFromFile(String sqlFilePath) {

		Project project = new Project();
		project.init();

		SQLExec e = new SQLExec();
		e.setProject(project);
		e.setTaskType("sql");
		e.setTaskName("sql");
		e.setSrc(new File(sqlFilePath));
		e.setDriver("org.hsqldb.jdbcDriver");
		e.setUserid("sa");
		e.setPassword("");
		e.setUrl(DB_URL);
		e.execute();
	}
}
