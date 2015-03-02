package com.binaryw.caculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class simpleCaculator extends Activity {
	private Button[] btnNum = new Button[11];// 数值按钮
	private Button[] btnCommand = new Button[5];// 符号按钮
	private TextView textView = null;// 显示区域
	private Button btnClear = null; // clear按钮
	private Button btnDel = null;// del 按钮
	private String lastCommand; // 用于保存运算符
	private boolean clearFlag; // 用于判断是否清空显示区域的值,true需要,false不需要
	private boolean firstFlag; // 用于判断是否是首次输入,true首次,false不是首次
	private double result; // 计算结果

	public simpleCaculator() {
		// 初始化各项值
		result = 0; // x的值
		firstFlag = true; // 是首次运算
		clearFlag = false; // 不需要清空
		lastCommand = "="; // 运算符
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layouot_main);
		// 获取运算符
		btnCommand[0] = (Button) findViewById(R.id.buttonPlus);
		btnCommand[1] = (Button) findViewById(R.id.buttonMinus);
		btnCommand[2] = (Button) findViewById(R.id.buttonMultiply);
		btnCommand[3] = (Button) findViewById(R.id.buttonDivide);
		btnCommand[4] = (Button) findViewById(R.id.buttonEval);
		// 获取数字
		btnNum[0] = (Button) findViewById(R.id.button0);
		btnNum[1] = (Button) findViewById(R.id.button1);
		btnNum[2] = (Button) findViewById(R.id.button2);
		btnNum[3] = (Button) findViewById(R.id.button3);
		btnNum[4] = (Button) findViewById(R.id.button4);
		btnNum[5] = (Button) findViewById(R.id.button5);
		btnNum[6] = (Button) findViewById(R.id.button6);
		btnNum[7] = (Button) findViewById(R.id.button7);
		btnNum[8] = (Button) findViewById(R.id.button8);
		btnNum[9] = (Button) findViewById(R.id.button9);
		btnNum[10] = (Button) findViewById(R.id.buttonDot);
		textView = (TextView) findViewById(R.id.resultText);

		textView.setText("0.0");
		// 添加监听事件
		NumberAction na = new NumberAction();
		CommandAction ca = new CommandAction();
		for (Button bc : btnCommand) {
			bc.setOnClickListener(ca);
		}
		for (Button bc : btnNum) {
			bc.setOnClickListener(na);
		}
		// clear按钮的动作
		btnClear = (Button) findViewById(R.id.buttonClear);
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				textView.setText("0.0");
				// 初始化各项值
				result = 0; // x的值
				firstFlag = true; // 是首次运算
				clearFlag = false; // 不需要清空
				lastCommand = "="; // 运算符
			}
		});
		btnDel = (Button) findViewById(R.id.buttonBackSpace);
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// Double currentNum =
				// Double.valueOf(textView.getText().toString());
				String currentStr = textView.getText().toString();
				if (currentStr.length() == 1) {
					currentStr = "0.0";
					result = 0; // x的值
					firstFlag = true; // 是首次运算
					clearFlag = false; // 不需要清空
					lastCommand = "="; // 运算符
				} else {
					currentStr = currentStr.substring(0,
							currentStr.length() - 1);
				}
				textView.setText(currentStr);
			}
		});
	}

	// 数字按钮监听器
	private class NumberAction implements OnClickListener {
		@Override
		public void onClick(View view) {
			Button btn = (Button) view;
			String input = btn.getText().toString();
			if (firstFlag) { // 首次输入
				// 一上就".",就什么也不做
				if (input.equals(".")) {
					return;
				}
				// 如果是"0.0"的话,就清空
				if (textView.getText().toString().equals("0.0")) {
					textView.setText("");
				}
				firstFlag = false;// 改变是否首次输入的标记值
			} else {
				String textViewStr = textView.getText().toString();
				// 判断显示区域的值里面是否已经有".",如果有,输入的又是".",就什么都不做
				if (textViewStr.indexOf(".") != -1 && input.equals(".")) {
					return;
				}
				// 判断显示区域的值里面只有"-",输入的又是".",就什么都不做
				if (textViewStr.equals("-") && input.equals(".")) {
					return;
				}
				// 判断显示区域的值如果是"0",输入的不是".",就什么也不做
				if (textViewStr.equals("0") && !input.equals(".")) {
					return;
				}
			}
			// 如果我点击了运算符以后,再输入数字的话,就要清空显示区域的值
			if (clearFlag) {
				textView.setText("");
				clearFlag = false;// 还原初始值,不需要清空
			}
			textView.setText(textView.getText().toString() + input);// 设置显示区域的值
		}
	}

	// 符号按钮监听器
	private class CommandAction implements OnClickListener {
		@Override
		public void onClick(View view) {
			Button btn = (Button) view;
			String inputCommand = (String) btn.getText();
			if (firstFlag) {// 首次输入"-"的情况
				if (inputCommand.equals("-")) {
					textView.setText("-");// 显示区域的内容设置为"-"
					firstFlag = false;// 改变首次输入的标记
				}
			} else {
				if (!clearFlag) {// 如果flag=false不需要清空显示区的值,就调用方法计算
					calculate(Double.parseDouble(textView.getText().toString()));// 保存显示区域的值,并计算
				}
				// 保存你点击的运算符
				lastCommand = inputCommand;
				clearFlag = true;// 因为我这里已经输入过运算符,
			}
		}
	}

	// 计算用的方法
	private void calculate(double x) {

		if (lastCommand.equals("+")) {
			result += x;
		} else if (lastCommand.equals("-")) {
			result -= x;
		} else if (lastCommand.equals("*")) {
			result *= x;
		} else if (lastCommand.equals("/")) {
			result /= x;
		} else if (lastCommand.equals("=")) {
			result = x;
		}
		textView.setText("" + result);
	}
}
