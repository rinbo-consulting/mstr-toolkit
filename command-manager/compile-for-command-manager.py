import xml.etree.cElementTree as ET
import sys

filename = sys.argv[1]

file_contents = open (filename, "r").read()

xmldoc = ET.Element("PROCEDURE_DEFINITION")
description = ET.SubElement(xmldoc, "DESCRIPTION")
code = ET.SubElement(xmldoc, "CODE")
sample = ET.SubElement(xmldoc, "SAMPLE")
version = ET.SubElement(xmldoc, "VERSION")
type = ET.SubElement(xmldoc, "TYPE")
input_params = ET.SubElement(xmldoc, "INPUT_PARAMS")
classpath = ET.SubElement(xmldoc, "CLASSPATH")
test_input = ET.SubElement(xmldoc, "TEST_INPUT")
test_conn_info = ET.SubElement(xmldoc, "TEST_CONNECTION_INFO")
test_psn_name = ET.SubElement(test_conn_info, "TEST_PSN_NAME")
test_username = ET.SubElement(test_conn_info, "TEST_USERNAME")
test_passwd = ET.SubElement(test_conn_info, "TEST_PASSWORD")

version.text = "1.0"
type.text = "CUSTOM"

code.text = file_contents

ET.ElementTree(xmldoc).write(filename + ".cmp")
