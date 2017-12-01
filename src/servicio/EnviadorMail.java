package servicio;

import javax.mail.*;
import javax.mail.internet.*;

import java.net.Authenticator;
import java.util.*;

/**
 *
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 * Se encarga del envio de correos electronicos. El correo se compone de un titulo y de un cuerpo donde es necesario un correo..
 * ..para efectuar dicha acción.
 *
 */
public class EnviadorMail {
    final String miCorreo = "notificadorsismosjava@gmail.com";
    final String miContraseña = "tareaprogramada1";
    final String servidorSMTP = "smtp.gmail.com";
    final String puertoEnvio = "465";
    String mailReceptor = null;
    String asunto = null;
    String cuerpo = null;

    /**
     * @param mailReceptor, se envia como parametro el email
     * @param asunto, titular del correo
     * @param cuerpo, encabezado o cuerpo del correo
     * Cumple la funcion del envio de correos. Se inicaliza la clase "autentificadorSMTP" con el fin de verificar los datos del emisor
     */
    public EnviadorMail(String mailReceptor, String asunto,String cuerpo) {

        this.mailReceptor = mailReceptor;
        this.asunto = asunto;
        this.cuerpo = cuerpo;

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.user", miCorreo);
        propiedades.put("mail.smtp.host", servidorSMTP);
        propiedades.put("mail.smtp.port", puertoEnvio);
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.socketFactory.port", puertoEnvio);
        propiedades.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        propiedades.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try { // Authenticator
            autentificadorSMTP auth = new autentificadorSMTP();
            Session session = Session.getInstance(propiedades, auth);

            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setText(cuerpo);
            mensaje.setSubject(asunto);
            mensaje.setFrom(new InternetAddress(miCorreo));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceptor));
            Transport.send(mensaje);
        } catch (Exception mex) {
            System.out.println("Error. Compruebe la conexión a internet!");
        }

    }

    /**
     * @author José Navarro, Hans Fernadez, Fabricio Elizondo
     * Cumple la funcion de verificar y validar el correo electronico y contraseña del emisor, con el fin de enviar el correo
     */
    private class autentificadorSMTP extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(miCorreo, miContraseña);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
