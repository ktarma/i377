package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import beans.Unit;

public class Dao extends AbstractDao {

	public void insertUnit(Unit unit) {
		String sql = "INSERT INTO unit" + "(id,name, code) VALUES" + "(NEXT VALUE FOR seq1,?,?)";
		try {
			pst = getConnection().prepareStatement(sql);
			pst.setString(1, unit.getName());
			pst.setString(2, unit.getCode());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public void deleteUnitById(int id) {
		String sql = "DELETE FROM unit WHERE id = ?";
		try {
			pst = getConnection().prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public List<Unit> findAllUnits(String searchstring) {

		List<Unit> units = new ArrayList<Unit>();
		try {
			if (searchstring != null && !searchstring.isEmpty()) {
				System.out.println("findAllUnits searchstring:'" + searchstring + "'");
				pst = getConnection().prepareStatement("SELECT * FROM unit " + "WHERE UPPER(name) like ?");
				pst.setString(1, "%" + searchstring.toUpperCase() + "%");
				rs = pst.executeQuery();
			} else {
				System.out.println("findAllUnits all");
				st = getConnection().createStatement();
				rs = st.executeQuery("select * from unit");
			}
			while (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getString(1));
				unit.setName(rs.getString(2));
				unit.setCode(rs.getString(3));
				units.add(unit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return units;
	}
	
	public void deleteAllUnits() {
		String sql = "DELETE FROM unit";
		try {
			System.out.println("deleteAllUnits");
			st = getConnection().createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}
}
