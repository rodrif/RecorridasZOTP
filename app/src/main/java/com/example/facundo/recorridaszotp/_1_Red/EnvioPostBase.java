public class EnvioPostBase {

    private String webUrl;
    private String query;

    public EnvioPostBase(String webUrl, String query) {
        this.webUrl = webUrl;
        this.query = query;
    }

    public String execute() {
        String charset = "UTF-8";
        URL url = null;
        HttpsURLConnection conn = null;
        InputStream inputStream = null;
        String respuesta = null;
        CertificateFactory cf = null;
        InputStream certificateInput = null;
        Certificate certificate = null;
        KeyStore keyStore = null;
        TrustManagerFactory tmf = null;
        String keyStoreType;
        SSLContext context = null;

        try {
            url = new URL(this.webUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Triggers POST.
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            conn.setRequestProperty(Utils.ACCESS_TOKEN, Config.getInstance().getAccessToken());
            conn.setRequestProperty(Utils.CLIENT, Config.getInstance().getClient());
            conn.setRequestProperty(Utils.UID, Config.getInstance().getUid());
            conn.setRequestProperty(Utils.TOKEN_TYPE, Config.getInstance().getTokenType());
            conn.setRequestProperty(Utils.EXPIRY, Config.getInstance().getExpiry());

            //Envio datos
            OutputStream output = conn.getOutputStream();
            output.write(this.query.getBytes(charset));

            if(conn.getResponseCode() == Utils.INVALID_TOKEN){
                this.onPostExecute(Integer.toString(Utils.INVALID_TOKEN));
            }

            //Leo respuesta
            Log.v(Utils.APPTAG, "Response Code: " + conn.getResponseCode());
            inputStream = new BufferedInputStream(conn.getInputStream());
            if(conn.getHeaderField(Utils.ACCESS_TOKEN) != null)
                Config.getInstance().setAccessToken(conn.getHeaderField(Utils.ACCESS_TOKEN));
            if(conn.getHeaderField(Utils.CLIENT) != null)
                Config.getInstance().setClient(conn.getHeaderField(Utils.CLIENT));
            if(conn.getHeaderField(Utils.EXPIRY) != null)
                Config.getInstance().setExpiry(conn.getHeaderField(Utils.EXPIRY));
            if(conn.getHeaderField(Utils.UID) != null)
                Config.getInstance().setUid(conn.getHeaderField(Utils.UID));
            respuesta = Utils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO deberia lanzar exception
        }
        return respuesta;
    }
}