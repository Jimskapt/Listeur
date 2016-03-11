package listeur.core;

import java.nio.file.Path ;

/**
 * Can be local path, FTP, samba, WebDAV, ...
 */
public class Source
{
	protected Path path=null;
	
	public Source(Path path)
	{
		this.setPath(path);
	}
	
	public void setPath(Path path)
	{
		this.path=path;
	}
	
	public String toString()
	{
		if( path!=null )
			return path.toString();
		else
			return "Unknown Source";
	}
}
