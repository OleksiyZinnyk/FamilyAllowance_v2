package studentorder.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studentorder.config.Config;
import studentorder.domain.CountryArea;
import studentorder.domain.PassportOffice;
import studentorder.domain.RegisterOffice;
import studentorder.domain.Street;
import studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {

    public static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);


    public final static String GET_STREET = "SELECT street_code, street_name " +
            "from jc_street where upper(street_name) like upper(?)";

    public final static String GET_PASSPOTR = "SELECT * " +
            "from jc_passport_office where p_office_area_id = ?";

    public final static String GET_REGISTER = "SELECT * " +
            "from jc_register_office where r_office_area_id = ?";

    public final static String GET_AREA = "SELECT * " +
            "from jc_country_struct where area_id like ? and area_id <> ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }


    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try (Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(GET_STREET);){

            stmt.setString(1,"%" + pattern + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
                result.add(str);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_PASSPOTR);){

            stmt.setString(1,areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PassportOffice str = new PassportOffice(
                        rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(str);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_REGISTER);){

            stmt.setString(1,areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegisterOffice str = new RegisterOffice(
                        rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(str);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_AREA);){

            String param1 = buildParam(areaId);
            String param2 = areaId;

            stmt.setString(1,param1);
            stmt.setString(2,param2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CountryArea str = new CountryArea(
                        rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(str);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException {
        if (areaId == null || areaId.trim().isEmpty()){
            return "__0000000000";
        }else if (areaId.endsWith("0000000000")){
            return areaId.substring(0,2) + "___0000000";
        } else if (areaId.endsWith("0000000")) {
            return areaId.substring(0,5) + "___0000";
        } else if (areaId.endsWith("0000")) {
            return areaId.substring(0,8) + "____";
        }
        throw new SQLException("Ivalid parameter 'areaId':" + areaId);
    }
}
