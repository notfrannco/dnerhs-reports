package py.com.data.reportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private ReporteDAO userDao;
    public JasperPrint exportPdfFile(String reportName, Map<String, Object> parameters) throws SQLException, JRException, IOException {
        return userDao.exportPdfFile(reportName, parameters);
    }
}
