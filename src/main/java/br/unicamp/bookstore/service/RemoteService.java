package br.unicamp.bookstore.service;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class RemoteService {

	private static final int TIMEOUT = 5000;

	public <T> T getAndParseXml(String endpointUrl, Class<T> xmlClass) throws Exception {
		Document document = getAndParseXml(endpointUrl);
		try {
			Element root = document.getDocumentElement();

			JAXBContext context = JAXBContext.newInstance(xmlClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<T> loader = unmarshaller.unmarshal(root, xmlClass);
			return loader.getValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Document getAndParseXml(String endpointUrl) throws Exception {
		URLConnection connection = openConnection(endpointUrl);
		return parseResponse(connection);
	}

	public Document postAndParseXml(String endpointUrl, String body)
			throws Exception {
		URLConnection connection = openConnection(endpointUrl, body);
		return parseResponse(connection);
	}

	private Document parseResponse(URLConnection connection) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			if (((HttpURLConnection) connection).getResponseCode() == 400) {
				throw new Exception("O CEP informado e invalido");
			}
			if (((HttpURLConnection) connection).getResponseCode() == 406) {
				throw new Exception("O codigo de rastreamento informado e invalido");
			}			
			if (((HttpURLConnection) connection).getResponseCode() == 412) {
				throw new Exception("Tipo de entrega indisponivel, favor informar tipo de entrega correto");
			}
			return builder.parse(connection.getInputStream());
		} catch (ParserConfigurationException | SAXException e) {
			throw new Exception(e);
		} catch (SocketTimeoutException e) {
			throw new Exception("Servico indisponivel");
		}
	}

	private URLConnection openConnection(String endpointUrl) throws Exception {
		try {
			URL url = new URL(endpointUrl);
			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			return connection;
		} catch (Exception e) {
			throw new Exception("Servico indisponivel");
		}
	}

	private URLConnection openConnection(String endpointUrl, String body)
			throws Exception {
		try {
			URL url = new URL(endpointUrl);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setAllowUserInteraction(false);

			PrintStream outStream = new PrintStream(
					connection.getOutputStream());
			outStream.println(body);
			outStream.close();
			return connection;
		} catch (Exception e) {
			throw new Exception("Servico indisponivel");
		}
	}

}
