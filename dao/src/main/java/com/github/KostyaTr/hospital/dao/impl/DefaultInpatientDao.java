package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.model.Inpatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultInpatientDao implements InpatientDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final InpatientDao HOLDER_INSTANCE = new DefaultInpatientDao();
    }

    public static InpatientDao getInstance() {
        return DefaultInpatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Inpatient> getInpatientsByDoctorId(Long doctorId) {
        final String sql = "select * from inpatient where doctor_id = ?";
        return getInpatients(doctorId, sql);
    }

    @Override
    public List<Inpatient> getInpatientsByDepId(Long deptId) {
        final String sql = "select * from inpatient " +
                "join chamber on inpatient.dept_chamber_id = chamber.id" +
                "where chamber.dept_id = ?;";
        return getInpatients(deptId, sql);
    }

    private List<Inpatient> getInpatients(Long id, String sql) {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return getInpatients(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inpatient> getUndiagnosedInpatientsByDep(Long deptId) {
        final String sql = "select * from inpatient " +
                "join chamber on inpatient.dept_chamber_id = chamber.id" +
                "where chamber.dept_id = ? and where inpatient.diagnose is null;";
        return getInpatients(deptId, sql);
    }

    @Override
    public List<Inpatient> getUndiagnosedInpatientsByDoctor(Long doctorId) {
        final String sql = "select * from inpatient where diagnose is null and doctor_id = ?;";
        return getInpatients(doctorId, sql);
    }

    private List<Inpatient> getInpatients(ResultSet resultSet) throws SQLException {
        List<Inpatient> inpatients = new ArrayList<>();
        while (resultSet.next()){
            final Inpatient user = new Inpatient(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("doctor_id"),
                    resultSet.getLong("dept_chamber_id"),
                    resultSet.getString("diagnose"),
                    resultSet.getLong("treatment_course_id"),
                    resultSet.getLong("operation_service_id"),
                    resultSet.getString("patient_status"),
                    resultSet.getDate("enrollment_date"));
            inpatients.add(user);
        }
        return inpatients;
    }

    @Override
    public List<Inpatient> getPatients() {
        final String sql = "select * from inpatient";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            return getInpatients(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        final String sql = "select * from inpatient where id = ?";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Inpatient(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getLong("doctor_id"),
                            resultSet.getLong("dept_chamber_id"),
                            resultSet.getString("diagnose"),
                            resultSet.getLong("treatment_course_id"),
                            resultSet.getLong("operation_service_id"),
                            resultSet.getString("patient_status"),
                            resultSet.getDate("enrollment_date"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addInpatient(Inpatient inpatient) {
        final String sql = "insert into inpatient" +
                "(user_id, doctor_id, dept_chamber_id, diagnose," +
                " treatment_course_id, operation_service_id, patient_status, enrollment_date) values" +
                "(?,?,?,?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, inpatient.getUserId());
            preparedStatement.setLong(2, inpatient.getDoctorId());
            preparedStatement.setLong(3, inpatient.getDeptChamberId());
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, inpatient.getStatus());
            preparedStatement.setDate(8, new java.sql.Date(inpatient.getEnrollmentDate().getTime()));
            preparedStatement.executeUpdate();
            try(ResultSet key = preparedStatement.getGeneratedKeys()){
                key.next();
                return key.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeInpatientById(Long patientId) {
        final String sql = "delete from inpatient where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateDiagnose(Long patientId, String diagnose) {
        final String sql = "update inpatient set diagnose = ? where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, diagnose);
            preparedStatement.setLong(2, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStatus(Long patientId, String status) {
        final String sql = "update inpatient set patient_status = ? where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTreatmentCourse(Long patientId, Long treatmentCourseId) {
        final String sql = "update inpatient set treatment_course_id = ? where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, treatmentCourseId);
            preparedStatement.setLong(2, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
