package cs;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cs.search.SearchKeyResult;
import cs.search.SearchResult;
import cs.search.SearchTypes;

public class ChineseSearch {

	private JFrame frame;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JTextArea txtrA;
	private JLabel label_3;
	private IChineseSearcher ics = ChineseSearcherFactory.getChineseSearcher();

	private JCheckBox[] jcbs = null;
	private SearchTypes[] st = { SearchTypes.WUBI_WORD, SearchTypes.WUBI_WORDS, SearchTypes.PINYIN_WORD,
			SearchTypes.PINYIN_WORDS };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final long initTimeMillis = System.currentTimeMillis();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChineseSearch window = new ChineseSearch();
					window.frame.setVisible(true);
					System.out.println(System.currentTimeMillis() - initTimeMillis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChineseSearch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(1000, 200, 460, 560);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("输入：");
		label.setBounds(10, 10, 54, 15);
		frame.getContentPane().add(label);

		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				System.out.println("ChineseSearch.initialize().new DocumentListener() {...}.removeUpdate()");
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("ChineseSearch.initialize().new DocumentListener() {...}.insertUpdate()");
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("ChineseSearch.initialize().new DocumentListener() {...}.changedUpdate()");
				search();
			}
		});
		textField.setBounds(61, 7, 363, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("输出：");
		label_1.setBounds(10, 33, 54, 15);
		frame.getContentPane().add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(61, 58, 363, 432);
		frame.getContentPane().add(scrollPane);

		txtrA = new JTextArea();
		txtrA.setEditable(false);
		scrollPane.setViewportView(txtrA);

		JPanel panel = new JPanel();
		panel.setBounds(61, 29, 363, 30);
		frame.getContentPane().add(panel);

		JCheckBox cb_wubiword = new JCheckBox("五笔字");
		cb_wubiword.setSelected(true);

		JCheckBox cb_wubiwords = new JCheckBox("五笔词");
		cb_wubiwords.setSelected(true);

		JCheckBox cb_pinyinword = new JCheckBox("拼音字");
		cb_pinyinword.setSelected(true);

		JCheckBox cb_pinyinwords = new JCheckBox("拼音词");
		cb_pinyinwords.setSelected(true);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(cb_wubiword);
		panel.add(cb_wubiwords);
		panel.add(cb_pinyinword);
		panel.add(cb_pinyinwords);

		JLabel label_2 = new JLabel("总数：");
		label_2.setBounds(10, 497, 54, 15);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("0");
		label_3.setBounds(61, 497, 70, 15);
		frame.getContentPane().add(label_3);
		jcbs = new JCheckBox[] { cb_wubiword, cb_wubiwords, cb_pinyinword, cb_pinyinwords };
	}

	private void search() {
		String inputStr = textField.getText();
		if (inputStr.length() == 0) {
			this.clear();
		} else {
			List<SearchTypes> list = new ArrayList<SearchTypes>();
			for (int i = 0; i < jcbs.length; i++) {
				if (jcbs[i].isSelected()) {
					list.add(st[i]);
				}
			}
			if (list.size() == 0) {
				this.clear();
			} else {
				SearchTypes[] sts = new SearchTypes[list.size()];
				list.toArray(sts);
				SearchResult sr = ics.search(textField.getText(), sts);
				SearchKeyResult[] skrs = sr.getSortedResults();
				StringBuilder sb = new StringBuilder();
				for (SearchKeyResult skr : skrs) {
					sb.append(skr.getKey() + " " + skr.getTotalValue() + "\n");
				}
				txtrA.setText(sb.toString());
				int min = scrollPane.getVerticalScrollBar().getMinimum();
				scrollPane.getVerticalScrollBar().setValue(min);
				scrollPane.getVerticalScrollBar().updateUI();
				label_3.setText(skrs.length + "");
			}
		}
	}

	private void clear() {
		txtrA.setText("");
		label_3.setText("0");
	}
}
