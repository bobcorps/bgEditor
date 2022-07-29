package com.bobsgame.editor;

import java.awt.*;

import java.awt.datatransfer.*;

import com.bobsgame.editor.Dialogs.StringDialog;
import com.bobsgame.editor.MultipleTileEditor.MTECanvas;

//===============================================================================================
public class SelectionArea
{//===============================================================================================



	public boolean isShowing = false;



	public Color color;
	public Color cutcolor;
	public Color copycolor;



	public int x1, y1, x2, y2;
	protected int[][][] copy;

	protected int copyWidth, copyHeight;

	public boolean isCopiedOrCut = false;



	//===============================================================================================
	public SelectionArea()
	{//===============================================================================================
		super();
	}

	//===============================================================================================
	public boolean contains(int x, int y)
	{//===============================================================================================
		if(x >= x1 && x < x2 && y >= y1 && y < y2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	//===============================================================================================
	public int getWidth()
	{//===============================================================================================
		return x2 - x1;
	}


	//===============================================================================================
	public int getHeight()
	{//===============================================================================================
		return y2 - y1;
	}


	//===============================================================================================
	public void setBackground(Color c)
	{//===============================================================================================
		color = c;
	}

	//===============================================================================================
	public void setLocation(int x, int y)
	{//===============================================================================================
		x1 = x;
		y1 = y;
	}

	//===============================================================================================
	public void moveSelectionBoxPositionByAmt(int x, int y)
	{//===============================================================================================
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
	}


	//===============================================================================================
	public int getX()
	{//===============================================================================================
		return x1;
	}

	//===============================================================================================
	public int getY()
	{//===============================================================================================
		return y1;
	}

	//===============================================================================================
	public void setLocation2(int x, int y)
	{//===============================================================================================
		x2 = x;
		y2 = y;

		if(x2<x1)x2=x1+1;
		if(y2<y1)y2=y1+1;

	}

	//===============================================================================================
	public void setSize(int x, int y)
	{//===============================================================================================
		x2 = x1 + x;
		y2 = y1 + y;

	}



	//===============================================================================================
	public void copySelectionCoordsToClipboard()
	{//===============================================================================================

		int tx1 = x1;
		int tx2 = x2;

		int ty1 = y1;
		int ty2 = y2;

		StringDialog sw = new StringDialog(new Frame());
		sw.show("");

		String n = sw.newName.getText();
		String l = n.substring(0, 1);
		String f = l.toUpperCase() + n.substring(1);

		StringSelection ss = new StringSelection(new String("if(action_range_xy_xy(" + tx1 + "*8," + ty1 + "*8," + tx2 + "*8," + ty2 + "*8,\"Look At " + f + "\"))\n{\n//" + f + "\n\n}\n"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);

	}

	public void copy()
	{
		// TODO Auto-generated method stub

	}

	public boolean paste()
	{
		return false;

	}

	public void cut()
	{
		// TODO Auto-generated method stub

	}

	public void delete()
	{
		// TODO Auto-generated method stub

	}

	public void cutkeys()
	{
		// TODO Auto-generated method stub

	}

	public void copykeys()
	{
		// TODO Auto-generated method stub

	}

	public boolean pastekeys()
	{
		return false;

	}

	public boolean pasteReverse()
	{
		return false;

	}

	public boolean pasteFlipped()
	{
		return false;

	}
}
