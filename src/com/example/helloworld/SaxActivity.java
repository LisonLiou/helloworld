package com.example.helloworld;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.utils.common.HttpDownloader;

public class SaxActivity extends Activity {

	private Button btnDownloadXml;
	private EditText editText1;
	private String fileContent;
	private TextView textViewFileContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.sax);

		btnDownloadXml = (Button) super.findViewById(R.id.btnDownloadXml);
		btnDownloadXml.setOnClickListener(new btnOnClickListener());

		editText1 = (EditText) super.findViewById(R.id.editText1);

		textViewFileContent = (TextView) super.findViewById(R.id.textViewFileContent);
	}

	class btnOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String downloadFileUrl = editText1.getText().toString();

			if (String.valueOf(downloadFileUrl) == "") {
				Toast.makeText(SaxActivity.this,
						"download file path is required", Toast.LENGTH_SHORT)
						.show();
			} else {
				HttpDownloader downloader = new HttpDownloader(downloadFileUrl);
				fileContent = downloader.download();
				textViewFileContent.setText(fileContent);
				
				//創建SAXParserFactory
				SAXParserFactory factory=SAXParserFactory.newInstance();
				try {
					XMLReader reader=factory.newSAXParser().getXMLReader();
					
					//爲XMLReader設置内容處理器
					reader.setContentHandler(new MyContentHandler());
					
					//開始解析對象
					reader.parse(new InputSource(new StringReader(fileContent)));
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}