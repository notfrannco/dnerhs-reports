package py.com.data.reportes;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ReportesRest {

    @Autowired
    private ReporteService reporteService;

    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatDateWithTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @RequestMapping(value= "/reportes", method = RequestMethod.GET)
    public void reporte(@RequestParam(value="name") String name, HttpServletRequest request,
                        HttpServletResponse response) throws JRException, IOException {

        System.out.println("name=" + name);

        Enumeration<String> params = request.getParameterNames();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        while(params.hasMoreElements()) {
            String param = params.nextElement().toString();
            if(!"name".equals(param)) {
                String value = request.getParameter(param);
                System.out.println(param + "=" + value);
                if (param.equals("telefono") || param.equals("cedula") || param.equals("autoridadCedula")){
                    paramMap.put(param, value);
                } else {
                    paramMap.put(param, castParameter(value));
                }
            }
        }

        DateTimeFormatter esDateFormatLargo =
                DateTimeFormatter
                        .ofPattern("dd 'de' MMMM 'de' yyyy")
                        .withLocale(new Locale("es", "PY"));
        paramMap.put("fechaConLetras", LocalDate.now().format(esDateFormatLargo));

        String fileName = name + ".pdf";
        String contentType = "application/pdf";

        try {
            JasperPrint jasperPrint = reporteService.exportPdfFile(name, paramMap);
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
            return;
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    private Object castParameter(String value) {

        // Fecha Hora?
        try {
            return formatDate.parse(value);
        } catch (ParseException pe) {}

        // Fecha
        try {
            return formatDateWithTime.parse(value);
        } catch (ParseException pe) {}

        // Entero?
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException nfe) {}

        // Decimal?
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfed) {}

        // String
        return value;

    }

}
