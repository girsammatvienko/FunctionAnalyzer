import java.awt.*;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.hws.jcm.awt.ExpressionInput;
import edu.hws.jcm.data.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class JFreeChartMainFrame extends JFrame {

    private JPanel programFunctionPage;
    private JPanel tableFunctionPage;
    private JPanel analyticFunctionPage;
    private JTextField textFieldAProgFunc;
    private JTextField textFieldA_AnFunc;
    private JTextField textFieldStartProgFunc;
    private JTextField textFieldStopProgFunc;
    private JTextField textFieldStepProgFunc;
    private JTextField textFieldStartAnalytFunc;
    private JTextField textFieldStopAnalytFunc;
    private JTextField textFieldStepAnalytFunc;
    private JTextField textFieldFunc;
    private XYSeries seriesFunProgFunc;
    private XYSeries seriesDerProgFunc;
    private XYSeries seriesFunAnalytFunc;
    private XYSeries seriesDerAnalytFunc;
    private XYSeries seriesPointTableFunc;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFreeChartMainFrame frame = new JFreeChartMainFrame();
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public JFreeChartMainFrame() {

        setVisible(false);
        setTitle("Graphic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 650);

        programFunctionPage = new JPanel();
        tableFunctionPage = new JPanel();
        analyticFunctionPage = new JPanel();

        programFunctionPage = addInterface(programFunctionPage, 1);
        tableFunctionPage = addInterface(tableFunctionPage, 2);
        analyticFunctionPage = addInterface(analyticFunctionPage, 3);
        setContentPane(programFunctionPage);
    }

    private double f(double a, double x) {
        return Math.exp(-x * x) * Math.sin(x);
    }

    private JPanel addInterface(JPanel contentPane, int pageNum) {

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelButtons = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
        flowLayout.setHgap(15);
        contentPane.add(panelButtons, BorderLayout.SOUTH);

        JPanel panelData = new JPanel();
        contentPane.add(panelData, BorderLayout.NORTH);

        switch (pageNum) {
            case 1:
                panelButtons = setButtonsPage1(panelButtons);
                panelData = setDataPage1(panelData);
                contentPane.add(new ChartPanel(createChart1()), BorderLayout.CENTER);
                break;
            case 2:
                panelButtons = setButtonsPage2(panelButtons);
                panelData = setDataPage2(panelData);
                contentPane.add(new ChartPanel(createChart2()), BorderLayout.CENTER);
                break;
            case 3:
                panelButtons = setButtonsPage3(panelButtons);
                panelData = setDataPage3(panelData);
                contentPane.add(new ChartPanel(createChart3()), BorderLayout.CENTER);
                break;
        }

        return contentPane;
    }

    private JPanel setButtonsPage1(JPanel buttonPanel) {
        JButton btnNewButton = new JButton("Plot");

        btnNewButton.addActionListener(e -> {
            double a = Double.parseDouble(textFieldAProgFunc.getText());
            double start = Double.parseDouble(textFieldStartProgFunc.getText());
            double stop = Double.parseDouble(textFieldStopProgFunc.getText());
            double step = Double.parseDouble(textFieldStepProgFunc.getText());

            Evaluatable der = new FFunction(Double.parseDouble(textFieldAProgFunc.getText()));

            seriesFunProgFunc.clear();
            seriesDerProgFunc.clear();

            for (double x = start; x < stop; x += step) {
                seriesFunProgFunc.add(x, f(a, x));
                seriesDerProgFunc.add(x ,NumMethods.der(x, 1.0e-4, der));
            }
        });

        buttonPanel.add(btnNewButton);

        JButton page2Button = new JButton("Next Page");
        page2Button.addActionListener(e -> {
            programFunctionPage.setVisible(false);
            tableFunctionPage.setVisible(true);
            setContentPane(tableFunctionPage);
        });
        buttonPanel.add(page2Button);

        JButton btnNewButtonExit = new JButton("Exit");
        btnNewButtonExit.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnNewButtonExit);

        return buttonPanel;
    }

    private JPanel setDataPage1(JPanel panelData){
        JLabel lblNewLabel = new JLabel("a: ");
        panelData.add(lblNewLabel);

        textFieldAProgFunc = new JTextField();
        textFieldAProgFunc.setText("1.0");
        panelData.add(textFieldAProgFunc);
        textFieldAProgFunc.setColumns(5);

        JLabel lblNewLabel0 = new JLabel("Start: ");
        panelData.add(lblNewLabel0);

        textFieldStartProgFunc = new JTextField();
        textFieldStartProgFunc.setText("-6.0");
        panelData.add(textFieldStartProgFunc);
        textFieldStartProgFunc.setColumns(5);

        JLabel lblNewLabel1 = new JLabel("Stop: ");
        panelData.add(lblNewLabel1);

        textFieldStopProgFunc = new JTextField();
        textFieldStopProgFunc.setText("6.0");
        panelData.add(textFieldStopProgFunc);
        textFieldStopProgFunc.setColumns(5);

        JLabel lblNewLabel2 = new JLabel("Step: ");
        panelData.add(lblNewLabel2);

        textFieldStepProgFunc = new JTextField();
        textFieldStepProgFunc.setText("0.01");
        panelData.add(textFieldStepProgFunc);
        textFieldStepProgFunc.setColumns(5);
        return panelData;
    }

    private JFreeChart createChart1() {

        seriesFunProgFunc = new XYSeries("Function");
        seriesDerProgFunc = new XYSeries("Derivative");

        double a = Double.parseDouble(textFieldAProgFunc.getText());
        double start = Double.parseDouble(textFieldStartProgFunc.getText());
        double stop = Double.parseDouble(textFieldStopProgFunc.getText());
        double step = Double.parseDouble(textFieldStepProgFunc.getText());

        Evaluatable der = new FFunction(Double.parseDouble(textFieldAProgFunc.getText()));

        for (double x = start; x < stop; x += step) {
            seriesFunProgFunc.add(x, f(a, x));
            seriesDerProgFunc.add(x ,NumMethods.der(x, 1.0e-4, der));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesFunProgFunc);
        dataset.addSeries(seriesDerProgFunc);

        JFreeChart chart = ChartFactory.createXYLineChart("y = exp(-x * x) * sin(x)", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        return chart;
    }

    private JPanel setButtonsPage2(JPanel buttonPanel) {
        JButton page1Button = new JButton("Prev Page");
        page1Button.addActionListener(e -> {
            tableFunctionPage.setVisible(false);
            programFunctionPage.setVisible(true);
            setContentPane(programFunctionPage);
        });
        buttonPanel.add(page1Button);

        JButton page3Button = new JButton("Next Page");
        page3Button.addActionListener(e -> {
            tableFunctionPage.setVisible(false);
            analyticFunctionPage.setVisible(true);
            setContentPane(analyticFunctionPage);
        });
        buttonPanel.add(page3Button);

        JButton btnNewButtonExit = new JButton("Exit");
        btnNewButtonExit.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnNewButtonExit);

        return buttonPanel;
    }

    private JPanel setDataPage2(JPanel panelData){
        return panelData;
    }

    private JFreeChart createChart2() {

        seriesPointTableFunc = new XYSeries("Function");

        XYSeriesCollection dataset = new XYSeriesCollection();

        FileListInterpolation flI = new FileListInterpolation();

        try {
            flI.readFromFile("TblFunc.dat");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        for (int i = 0; i < flI.numPoints(); i++) {
            Point2D temp = flI.getPoint(i);
            seriesPointTableFunc.add(temp.getX(), temp.getY());
        }

        dataset.addSeries(seriesPointTableFunc);

        JFreeChart chart = ChartFactory.createXYLineChart("sin(x)", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        return chart;
    }

    private JPanel setButtonsPage3(JPanel buttonPanel) {
        JButton btnNewButton = new JButton("Plot");

        btnNewButton.addActionListener(e -> {
            double a = Double.parseDouble(textFieldA_AnFunc.getText());
            double start = Double.parseDouble(textFieldStartAnalytFunc.getText());
            double stop = Double.parseDouble(textFieldStopAnalytFunc.getText());
            double step = Double.parseDouble(textFieldStepAnalytFunc.getText());

            Parser parser = new Parser();
            Variable varX = new Variable("x");
            Constant varA = new Constant("a", a);
            parser.add(varX);
            parser.add(varA);
            ExpressionInput input = new ExpressionInput(textFieldFunc.getText(), parser);
            Function function = input.getFunction(varX);
            Function der = function.derivative(1);

            seriesFunAnalytFunc.clear();
            seriesDerAnalytFunc.clear();

            for (double i = start; i < stop; i += step) {
                seriesFunAnalytFunc.add(i, function.getVal(new double[]{i}));
                seriesDerAnalytFunc.add(i, der.getVal(new double[]{i}));
            }
        });

        buttonPanel.add(btnNewButton);

        JButton prevPage = new JButton("Prev Page");
        prevPage.addActionListener(e -> {
            analyticFunctionPage.setVisible(false);
            tableFunctionPage.setVisible(true);
            setContentPane(tableFunctionPage);
        });
        buttonPanel.add(prevPage);

        JButton btnNewButtonExit = new JButton("Exit");
        btnNewButtonExit.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnNewButtonExit);

        return buttonPanel;
    }

    private JPanel setDataPage3(JPanel panelData){
        JLabel lblNewLabel = new JLabel("a: ");
        panelData.add(lblNewLabel);

        textFieldA_AnFunc = new JTextField();
        textFieldA_AnFunc.setText("1.0");
        panelData.add(textFieldA_AnFunc);
        textFieldA_AnFunc.setColumns(5);

        JLabel lblNewLabel0 = new JLabel("Start: ");
        panelData.add(lblNewLabel0);

        textFieldStartAnalytFunc = new JTextField();
        textFieldStartAnalytFunc.setText("-6.0");
        panelData.add(textFieldStartAnalytFunc);
        textFieldStartAnalytFunc.setColumns(5);

        JLabel lblNewLabel1 = new JLabel("Stop: ");
        panelData.add(lblNewLabel1);

        textFieldStopAnalytFunc = new JTextField();
        textFieldStopAnalytFunc.setText("6.0");
        panelData.add(textFieldStopAnalytFunc);
        textFieldStopAnalytFunc.setColumns(5);

        JLabel lblNewLabel2 = new JLabel("Step: ");
        panelData.add(lblNewLabel2);

        textFieldStepAnalytFunc = new JTextField();
        textFieldStepAnalytFunc.setText("0.01");
        panelData.add(textFieldStepAnalytFunc);
        textFieldStepAnalytFunc.setColumns(5);

        JLabel lblNewLabel3 = new JLabel("Function: ");
        panelData.add(lblNewLabel3);

        textFieldFunc = new JTextField();
        textFieldFunc.setText("x^3/3");
        panelData.add(textFieldFunc);
        textFieldFunc.setColumns(15);

        return panelData;
    }

    private JFreeChart createChart3() {

        seriesFunAnalytFunc = new XYSeries("Function");
        seriesDerAnalytFunc = new XYSeries("Derivative");

        double a = Double.parseDouble(textFieldA_AnFunc.getText());
        double start = Double.parseDouble(textFieldStartAnalytFunc.getText());
        double stop = Double.parseDouble(textFieldStopAnalytFunc.getText());
        double step = Double.parseDouble(textFieldStepAnalytFunc.getText());

        Parser parser = new Parser();
        Variable varX = new Variable("x");
        Constant varA = new Constant("a", a);
        parser.add(varX);
        parser.add(varA);
        ExpressionInput input = new ExpressionInput(textFieldFunc.getText(), parser);
        Function function = input.getFunction(varX);
        Function der = function.derivative(1);

        for (double i = start; i < stop; i += step) {
            seriesFunAnalytFunc.add(i, function.getVal(new double[]{i}));
            seriesDerAnalytFunc.add(i, der.getVal(new double[]{i}));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesFunAnalytFunc);
        dataset.addSeries(seriesDerAnalytFunc);

        JFreeChart chart = ChartFactory.createXYLineChart("", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        return chart;
    }
}