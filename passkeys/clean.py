#!/usr/bin/env python3

import os,sys,time,re 

if __name__ == '__main__' :
	contents = open('/Users/jlong/Downloads/passkeys/target/joshs-native-image/reflect-config.json' ,'r').read()
	results =  re.findall(r'"org.springframework.security\..*"' , contents )
	for r in results : 
		r = r[1:-1]
		print (r)