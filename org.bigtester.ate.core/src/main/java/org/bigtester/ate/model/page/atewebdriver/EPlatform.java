/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.page.atewebdriver;

/**
 * This class EPlatfrom defines ....
 * @author Jun Yang
 *
 */
public enum EPlatform {  
    
    /** The Any. */
    Any("any"),  
    
    /** The Linux. */
    Linux_32("32bit Linux"),
    Linux_64("64bit Linux"),
    
    /** The Mac_os. */
    Mac_OS("Mac OS"),  
    
    /** The Mac_os_x. */
    Mac_OS_X_32("32bit Mac OS X"),  
    Mac_OS_X_64("64bit Mac OS X"),  
    
    /** The Windows. */
    Windows_32("32bit Windows"),  
    Windows_64("32bit Windows"),
    
    /** The O s2. */
    OS2("OS/2"),  
    
    /** The Solaris. */
    Solaris("Solaris"),  
    
    /** The Sun os. */
    SunOS("SunOS"),  
    
    /** The MP ei x. */
    MPEiX("MPE/iX"),  
    
    /** The hp ux. */
    HP_UX("HP-UX"),  
    
    /** The aix. */
    AIX("AIX"),  
    
    /** The O s390. */
    OS390("OS/390"),  
    
    /** The Free bsd. */
    FreeBSD("FreeBSD"),  
    
    /** The Irix. */
    Irix("Irix"),  
    
    /** The Digital_ unix. */
    Digital_Unix("Digital Unix"),  
    
    /** The Net ware_411. */
    NetWare_411("NetWare"),  
    
    /** The OS f1. */
    OSF1("OSF1"),  
    
    /** The Open vms. */
    OpenVMS("OpenVMS"),  
    
    /** The Others. */
    Others("Others");  
      
    /**
     * Instantiates a new e platform.
     *
     * @param desc the desc
     */
    private EPlatform(String desc){  
        description = desc;  
    }  
    
    /**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** The description. */
    private String description;  
}  