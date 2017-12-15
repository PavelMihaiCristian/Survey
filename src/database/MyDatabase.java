package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Question;
import model.Respondent;
import model.Survey;

public class MyDatabase {

	private String url;
	private String user;
	private String pw;
	private static Connection conn;

	final static String connectString = "jdbc:oracle:thin:@localhost:1521:ORACLEDWH";
	final static String userName = "SEP4";
	final static String password = "SEP4";

	public MyDatabase() throws SQLException {
		this(connectString, userName, password);
	}

	public MyDatabase(String url, String user, String pw) throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		this.url = url;
		this.user = user;
		this.pw = pw;
		conn = null;
	}

	public MyDatabase(String user, String pass) throws SQLException {
		this(connectString, user, pass);
	}

	public void openDatabase() throws SQLException {
		conn = DriverManager.getConnection(connectString, userName, password);
		conn.setAutoCommit(false);
	}

	public void closeDatabase() throws SQLException {
		conn.close();
	}

	public ArrayList<Object[]> query(String sql, Object... statementElements)
			throws SQLException {
		openDatabase();

		PreparedStatement statement = null;
		ArrayList<Object[]> list = null;
		ResultSet resultSet = null;
		if (sql != null && statement == null) {
			statement = conn.prepareStatement(sql);
			if (statementElements != null) {
				for (int i = 0; i < statementElements.length; i++)
					statement.setObject(i + 1, statementElements[i]);
			}
		}
		resultSet = statement.executeQuery();
		list = new ArrayList<Object[]>();
		while (resultSet.next()) {
			Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
			for (int i = 0; i < row.length; i++) {
				row[i] = resultSet.getObject(i + 1);
			}
			list.add(row);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		closeDatabase();
		return list;
	}

	public int update(String sql, Object... statementElements)
			throws SQLException {
		openDatabase();
		PreparedStatement statement = conn.prepareStatement(sql);
		if (statementElements != null) {
			for (int i = 0; i < statementElements.length; i++)
				statement.setObject(i + 1, statementElements[i]);
		}

		int result = statement.executeUpdate();

		closeDatabase();
		return result;
	}

	public  boolean writeToDB(Question[] questions,
			ArrayList<Respondent> respondants, Survey survey) {
		insertSurveyInfo(survey);
		writeInABatchQuestions(questions, survey);
		for (int i = 0; i < questions.length; i++) {
			questions[i].setInserted(false);
		}
		writeInABatchAnswers(questions);
		insertResponses(respondants);
		return true;
	}

	public  boolean insertSurveyInfo(Survey survey) {

		String sql = "insert into survey(title,description,year) values(?,?,?)";

		int result = -1;
		try {
			openDatabase();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, survey.getTitle());
			statement.setString(2, survey.getDescription());
			statement.setInt(3, survey.getYear());
			result = statement.executeUpdate();
			statement.close();
			closeDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (result == 1);
	}

	public  int[] writeInABatchQuestions(Question[] questions, Survey survey) {

		PreparedStatement preparedStatementQuestion,preparedStatementSurveyid;
		ResultSet result=null;
		long surveyId=0;
		long start = System.currentTimeMillis();
		try {
			openDatabase();
			String Ssql="select surveyid from survey where title=?";
			String Qsql = "insert into questions "
					+ "(text, questionCat,questiontype,surveyid)"
					+ " values (?, ?, ? , ?)";
			preparedStatementSurveyid=conn.prepareStatement(Ssql);
			preparedStatementSurveyid.setString(1, survey.getTitle());
			
			result = (ResultSet) preparedStatementSurveyid
					.executeQuery();
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while (result.next()) {
				Object[] row = new Object[result.getMetaData()
						.getColumnCount()];
				for (int k = 0; k < row.length; k++) {
					row[k] = result.getObject(k + 1);
				}
				list.add(row);
			}
			if (list.size() > 0) {
				surveyId = Long.parseLong(list.get(0)[0] + "");
			}
			
			preparedStatementQuestion = conn.prepareStatement(Qsql);
			for (int index = 0; index < questions.length; index++) {
				Question q = questions[index];
				if (!q.isDiscard() && !q.isInserted()) {
					preparedStatementQuestion.setString(1, q.getText());
					preparedStatementQuestion.setString(2, q.getCategory()
							.toString());
					preparedStatementQuestion.setString(3, q.getType().toString());
					preparedStatementQuestion.setLong(4, surveyId);
					preparedStatementQuestion.addBatch();
				}
				q.setInserted(true);
			}
			int[] inserted = preparedStatementQuestion.executeBatch();

			long end = System.currentTimeMillis();

			System.out.println("total time taken to insert the QUESTIONS = "
					+ (end - start) + " ms");
			preparedStatementSurveyid.close();
			preparedStatementQuestion.close();
			closeDatabase();

			return inserted;

		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				ex = ex.getNextException();
			}
			throw new RuntimeException("Error");
		}
	}

	public  int[] writeInABatchAnswers(Question[] questions) {
		
		PreparedStatement preparedStatementQuestionId, preparedStatementAnswer;
		ResultSet result=null;
		
		long start = System.currentTimeMillis();
		long qid=0;
		try {
			openDatabase();

			String Asql = "insert into possibleanswers (questionid, answer)"
					+ "values (?, ?)";
			String query = "select questionid from questions where text = ?";
			preparedStatementAnswer = conn.prepareStatement(Asql);
			preparedStatementQuestionId = conn.prepareStatement(query);
			

			for (int i = 0; i < questions.length; i++) {
				if (!questions[i].isDiscard() && !questions[i].isInserted()) {
					preparedStatementQuestionId.setString(1, questions[i].getText());
					
					result = (ResultSet) preparedStatementQuestionId
							.executeQuery();
					ArrayList<Object[]> list = new ArrayList<Object[]>();
					while (result.next()) {
						Object[] row = new Object[result.getMetaData()
								.getColumnCount()];
						for (int k = 0; k < row.length; k++) {
							row[k] = result.getObject(k + 1);
						}
						list.add(row);
					}
					if (list.size() > 0) {
						qid = Long.parseLong(list.get(0)[0] + "");
					}
					
					if (qid!=0) {
						ArrayList<String> ans = questions[i].getAnswers();
						for (int j = 0; j < ans.size(); j++) {
							preparedStatementAnswer.setLong(1, qid);
							String answer = ans.get(j);
							if (answer.equals("")) {
								answer = "No Answer";
							}
							preparedStatementAnswer.setString(2, answer);
							preparedStatementAnswer.addBatch();
						}
					}
				}
				questions[i].setInserted(true);
			}
			int[] inserted = preparedStatementAnswer.executeBatch();
			long end = System.currentTimeMillis();

			System.out
					.println("total time taken to insert the POSSIBLE ANSWERS = "
							+ (end - start) + " ms");
			preparedStatementQuestionId.close();
			preparedStatementAnswer.close();
			closeDatabase();

			return inserted;

		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				ex = ex.getNextException();
			}
			throw new RuntimeException("Error");
		}
	}

	public  void insertResponses(ArrayList<Respondent> Respondents) {
		String update = "insert into respondantQA (respondantID, questionID, answerId) "
				+ "values (?, ?, ?)";
		String query = "select questionid from questions where text = ?";
		String nextID= "select sqSurvey.nextval from dual";
		String answerIdsql="select answerid from possibleanswers where questionid=? and answer=?";
		long respondantID=0;
		long answerId=0;
		long questionID = 0;
		ResultSet result = null;
		int[] inserted = null;
		PreparedStatement preparedStatementQuestionID, preparedStatementRespondantAnswers,
		preparedStatementRespondentId,preparedStatementAnswerId;

		try {
			long start = System.currentTimeMillis();
			openDatabase();
			preparedStatementQuestionID = conn.prepareStatement(query);
			preparedStatementRespondantAnswers = conn.prepareStatement(update);
			preparedStatementRespondentId=conn.prepareStatement(nextID);
			preparedStatementAnswerId=conn.prepareStatement(answerIdsql);
			
			for (int i = 0; i < Respondents.size(); i++) {
				Respondent person;
				ArrayList<Question> personQA;
			
				result = (ResultSet) preparedStatementRespondentId
						.executeQuery();
				ArrayList<Object[]> list = new ArrayList<Object[]>();
				while (result.next()) {
					Object[] row = new Object[result.getMetaData()
							.getColumnCount()];
					for (int k = 0; k < row.length; k++) {
						row[k] = result.getObject(k + 1);
					}
					list.add(row);
				}

				if (list.size() > 0) {
					respondantID = Long.parseLong(list.get(0)[0] + "");
				}

				person = Respondents.get(i);
				personQA = person.getQa();
				for (int j = 0; j < personQA.size(); j++) {
					questionID = 0;
					Question personOneQA = personQA.get(j);

					preparedStatementQuestionID.setString(1,
							personOneQA.getText());
					result = (ResultSet) preparedStatementQuestionID
							.executeQuery();
					list = new ArrayList<Object[]>();
					while (result.next()) {
						Object[] row = new Object[result.getMetaData()
								.getColumnCount()];
						for (int k = 0; k < row.length; k++) {
							row[k] = result.getObject(k + 1);
						}
						list.add(row);
					}

					if (list.size() > 0) {
						questionID = Integer.parseInt(list.get(0)[0] + "");
					}
					
					// questionID = Integer.parseInt(list.get(0)[0] + "");
					if (questionID != 0) {						
						
						ArrayList<String> answersPerQuestion = personOneQA
								.getAnswers();
						for (int k = 0; k < answersPerQuestion.size(); k++) {
							
							answerId=0;
							preparedStatementAnswerId.setLong(1, questionID);
							preparedStatementAnswerId.setString(2, answersPerQuestion.get(k));
							
							result = (ResultSet) preparedStatementAnswerId
									.executeQuery();
							list = new ArrayList<Object[]>();
							while (result.next()) {
								Object[] row = new Object[result.getMetaData()
										.getColumnCount()];
								for (int l = 0; l < row.length; l++) {
									row[l] = result.getObject(l + 1);
								}
								list.add(row);
							}

							if (list.size() > 0) {
								answerId = Long.parseLong(list.get(0)[0] + "");
							}
							if (answerId!=0) {
								preparedStatementRespondantAnswers.setLong(1,
										respondantID);
								preparedStatementRespondantAnswers.setLong(2,
										questionID);
								preparedStatementRespondantAnswers.setLong(3,answerId);
								preparedStatementRespondantAnswers.addBatch();
							}
						}
						inserted = preparedStatementRespondantAnswers
								.executeBatch();
					}

				}

			}
			long end = System.currentTimeMillis();

			System.out
					.println("total time taken to insert the Respondants ANSWERS = "
							+ (end - start) + " ms");
			preparedStatementQuestionID.close();
			preparedStatementRespondantAnswers.close();
			preparedStatementAnswerId.close();
			preparedStatementRespondentId.close();
			closeDatabase();

		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				ex = ex.getNextException();
			}
			throw new RuntimeException("Error");
		}

	}
	
	public void prepareForExtract(){
		try {
			openDatabase();
			CallableStatement stm=conn.prepareCall("{call dropTables}");
			stm.execute();
			stm.close();
			closeDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
