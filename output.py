import pandas as pd
import matplotlib.pyplot as mlp
import matplotlib.ticker as mlt
import numpy as np

ruleNames = []
columnRuleNames = []
resultFromColumnRules = []

def isfloat(value):
	try:
		float(value)
		return True
	except Exception:
		return False

def isint(value):
	try:
		int(value)
		return True
	except Exception:
		return False

path = "C:/Users/ax111/Downloads/users.csv"
df = pd.read_csv(path, dtype=str)
idColSet = "id"

_exactNameRule = []
def isNancy(row):
	 return row["firstname"] == "Nancy"
_exactNameRule.append(isNancy)

def isRobert(row):
	 return row["firstname"] == "robert"
_exactNameRule.append(isRobert)

def isSteven(row):
	 return row["firstname"] == "Steven"
_exactNameRule.append(isSteven)

def exactNameRule():
	result = []
	tempRes = False
	for index, row in df.iterrows():
		try:
			for func in _exactNameRule:
				tempRes = tempRes or func(row)
			if tempRes:
				result.append("True")
			else:
				result.append("False")
			tempRes = False
		except Exception:
			result.append("False")
	return pd.Series(result)
ruleNames.append("exactNameRule")
df["exactNameRule"] = exactNameRule()

_nameRuleWhere = []
_nameRule = []
def nameRule3Where(row):
	return float(row["balance"])>-5000.0
_nameRuleWhere.append(nameRule3Where)

def nameRule3():
	return df["firstname"].count() == 9
_nameRule.append(nameRule3)

def nameRule4Where(row):
	return float(row["balance"])>-5000.0
_nameRuleWhere.append(nameRule4Where)

def nameRule4():
	return len(df["firstname"].unique()) == 9
_nameRule.append(nameRule4)

def nameRule():
	try:
		tempDf = pd.DataFrame(columns=df.columns)
		tempBool = False
		tempRes = False
		for index, row in df.iterrows():
			for func in _nameRuleWhere:
				tempBool = tempBool or func(row)
			if tempBool:
				tempDf = tempDf.append(row)

			for func in _nameRule:
				tempRes = tempRes or func(row)
			if tempRes:
				return ['x', ' ']
			else:
				return [' ', 'x']
	except Exception:
		return [' ', 'x']
columnRuleNames.append("nameRule")
resultFromColumnRules.append(nameRule())

def balanceRule2():
	result = []
	for index, row in df.iterrows():
		try:
			if (float(row["balance"])>0.0 and float(row["balance"])<100000.0):
				result.append("True")
			else:
				result.append("False")
		except Exception:
			result.append("False")
	return pd.Series(result)
ruleNames.append("balanceRule2")
df["balanceRule2"] = balanceRule2()

def heigtRule1():
	result = []
	for index, row in df.iterrows():
		try:
			if isfloat(row["height"]):
				result.append("True")
			else:
				result.append("False")
		except Exception:
			result.append("False")
	return pd.Series(result)
ruleNames.append("heigtRule1")
df["heigtRule1"] = heigtRule1()

def avgHeightRule():
	if pd.to_numeric(df["height"], downcast='float').mean()>120:
		return ['x', ' ']
	else:
		return [' ', 'x']
columnRuleNames.append("avgHeightRule")
resultFromColumnRules.append(avgHeightRule())

def sumHeightRule():
	if pd.to_numeric(df["height"], downcast='float').sum() == 1583:
		return ['x', ' ']
	else:
		return [' ', 'x']
columnRuleNames.append("sumHeightRule")
resultFromColumnRules.append(sumHeightRule())

def ageRule1():
	result = []
	for index, row in df.iterrows():
		try:
			if (isint(row["age"]) and int(row["age"])>0 and int(row["age"])<100):
				result.append("True")
			else:
				result.append("False")
		except Exception:
			result.append("False")
	return pd.Series(result)
ruleNames.append("ageRule1")
df["ageRule1"] = ageRule1()

def pretty_print(analyzeRuleTable, columnTable):
	fig, (overviewAxis, normalAxis, columnAxis) = mlp.subplots(3, 1)
	colors_list = ['#5cb85c', '#d9534f']
	overviewPlot = (analyzeRuleTable.div(analyzeRuleTable.sum(1), axis=0)).plot(
	kind='barh', 
	stacked=True, 
	color=colors_list, 
	edgecolor='white', 
	ax=overviewAxis
	)
	rightSide = overviewPlot.spines['right']
	rightSide.set_visible(False)
	topSide = overviewPlot.spines['top']
	topSide.set_visible(False)
	overviewAxis.set_title('Overview of failures in rules:', fontsize='large')
	overviewAxis.xaxis.set_major_formatter(mlt.PercentFormatter(1))
	overviewAxis.legend(['Passed', 'Failed'], loc=[1, 0.5])

	analyzeRuleTable["Total"] = analyzeRuleTable["True"] + analyzeRuleTable["False"] 
	analyzeRuleTable["%"] = round((analyzeRuleTable["True"] / analyzeRuleTable["Total"]) * 100, 2)

	column_labels = ['No. of passed rows', 'No. of failed rows', 'no. of total rows', '% of passed rows']
	normalAxis.axis('off')
	norPlot = normalAxis.table(
		cellText=analyzeRuleTable.values, 
		colLabels=column_labels, 
		rowLabels=ruleNames,
		bbox=[0, 0.2, 1, 0.65],
		loc='center'
		)
	norPlot.auto_set_font_size(False)
	norPlot.set_fontsize(12)

	column_labels = ['Passed', 'Failed', 'Error message']
	columnAxis.axis('off')
	colPlot = columnAxis.table(
		cellText=columnTable.values, 
		colLabels=column_labels, 
		rowLabels=columnRuleNames,
		#bbox=[0, 0, 1, 0.65],
		loc='center'
	)
	colPlot.set_fontsize(12)

	mlp.tight_layout()
	mlp.show()

def ANALYZE():
	columnTable = pd.DataFrame.from_records(resultFromColumnRules)
	totalFailure = 0
	for row in df.iterrows():
		for rule in ruleNames:
			if (row[1][rule] == False):
				totalFailure += 1
				break
	totalRows = len(df.index)
	totalFailure = round((totalFailure / totalRows) * 100, 2)
	overrall = pd.DataFrame([[totalFailure, 100-totalFailure]], columns=["False", "True"]).rename(index={0: "Overrall correctness"})
	cols = df[ruleNames].apply(pd.value_counts).fillna(0).transpose()
	cols = cols.append(overrall)
	ruleNames.append("Overral correctness")
	analyzeRuleTable = pd.DataFrame(cols["True"])
	analyzeRuleTable["False"] = cols["False"]
	pretty_print(analyzeRuleTable, columnTable)
ANALYZE()
