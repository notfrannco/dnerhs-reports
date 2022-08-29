package py.com.data.reportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Transactional
@Repository
public class ReporteDAO {

    @Value("${jasperFilesFolder}")
    private String JASPER_FILES_FOLDER;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public JasperPrint exportPdfFile(String reportName, Map<String, Object> parameters) throws SQLException, JRException, IOException {

		try(Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			String jasper = JASPER_FILES_FOLDER + File.separator + reportName + ".jasper";
			JasperPrint print = JasperFillManager.fillReport(jasper, parameters, conn);
			return print;
		}

    }

}
