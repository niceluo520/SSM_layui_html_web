package com.cbox.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
    static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;

    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    public static String urlEncodeGBK(String source) {
        String result = source;
        try {
            result = URLEncoder.encode(source, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    public static String httpRequest(String req_url) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return buffer.toString();
    }

    public static InputStream httpRequestIO(String requestUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            inputStream = httpUrlConn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static String sendGet(String url, String param) {
        String urlNameString = url + "?" + param;
        return sendGet(urlNameString);
    }

    public static String sendGet(String url) {
        String result;
        result = "";
        BufferedReader in = null;
        try {
            String line;
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        String result;
        OutputStreamWriter out = null;
        BufferedReader in = null;
        result = "";
        try {
            String line;
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(120000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPostIscJson(HttpServletRequest request, String url, String p_code, String sv_mothod, JSONObject jsonObject) {
        JSONObject param = new JSONObject();
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(p_code)) {
            json.put("p_code", p_code);
        }
        json.put("sv_method", sv_mothod);
        param.put("sv_header", json);
        param.put("sv_params", jsonObject);
        String result;
        OutputStreamWriter out = null;
        BufferedReader in = null;
        result = "";
        try {
            String cookie = "";
            String sessionLoginTick = (String) request.getSession().getAttribute("loginTicket");
            if (StringUtils.isNotBlank(sessionLoginTick)) {
                cookie = "login_cookie_key=" + sessionLoginTick;
            } else {
                cookie = request.getHeader("Cookie");
            }
            String line;
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(120000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", cookie);
            conn.setRequestProperty("referer", request.getHeader("referer"));
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param.toJSONString());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return result;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return result;
            }
        }
        return result;
    }

    public static String sendPostIscJson(String url, String p_code, String sv_mothod, JSONObject jsonObject) {
        JSONObject param = new JSONObject();
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(p_code)) {
            json.put("p_code", p_code);
        }
        json.put("sv_method", sv_mothod);
        param.put("sv_header", json);
        param.put("sv_params", jsonObject);
        String result;
        OutputStreamWriter out = null;
        BufferedReader in = null;
        result = "";
        try {
            String line;
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(120000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param.toJSONString());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return result;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return result;
            }
        }
        return result;
    }

    public static String post(String url, NameValuePair[] params, String contextType) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Content-Type", StringUtils.isNotBlank((String) contextType) ? contextType : "application/x-www-form-urlencoded; charset=UTF-8");
        post.getParams().setParameter("http.protocol.content-charset", (Object) "UTF-8");
        post.setRequestBody(params);
        PostMethod method = post;
        client.executeMethod((HttpMethod) method);
        StringBuffer sb = new StringBuffer(512);
        BufferedReader br = null;
        InputStream is = null;
        is = method.getResponseBodyAsStream();
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return new String(sb.toString());
    }

    public static void downloadFile(String downloadUrl, String fileName) throws Exception {
        int b;
        InputStream fis = HttpUtil.httpRequestIO(downloadUrl);
        if (null == fis) {
            throw new Exception("下载路径请求异常！");
        }
        File file = new File(fileName);
        FileOutputStream outstream = new FileOutputStream(file);
        byte[] temp = new byte[1024];
        while ((b = fis.read(temp)) != -1) {
            outstream.write(temp, 0, b);
        }
        outstream.flush();
        outstream.close();
        fis.close();
    }

    /**
     * 
     * downloadFile:下载远程文件
     *
     * @date: 2019年4月15日 上午10:20:29
     * @author cbox
     * @param downloadUrl 下载地址
     * @param downloadDir 下载保存文件夹路径
     * @param fileName 下载保存文件名
     * @return
     * @throws Exception
     */
    public static File downloadFile(String downloadUrl, String downloadDir, String fileName) throws Exception {
        File file = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            // 文件大小
            // int fileLength = httpUrlConn.getContentLength();
            InputStream fis = httpUrlConn.getInputStream();
            if (null == fis) {
                throw new Exception("下载路径请求异常！");
            }
            String path = "";
            if (null != fileName && !"".equals(fileName)) {
                path = downloadDir + fileName;
            } else {// 默认文件名用下载端的文件名
                String filePathUrl = httpUrlConn.getURL().getFile();
                String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf("=") + 1);
                path = downloadDir + fileFullName;
            }
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            int size = 0;
            // int len = 0;
            FileOutputStream outstream = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            while ((size = fis.read(temp)) != -1) {
                // len += size;
                outstream.write(temp, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
            }
            outstream.flush();
            outstream.close();
            fis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
