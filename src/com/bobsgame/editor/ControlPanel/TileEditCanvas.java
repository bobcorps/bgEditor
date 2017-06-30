

package com.bobsgame.editor.ControlPanel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import com.bobsgame.EditorMain;
import com.bobsgame.editor.Project.Project;
//===============================================================================================
public class TileEditCanvas extends JComponent implements MouseMotionListener, MouseListener, ActionListener, ItemListener
{//===============================================================================================
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EditorMain E;

	public int EditGrid_x = 16;
	public int EditGrid_y = 16;

	public int EditZoom = 16;




	public TileEditCanvas()
	{

	}

	//===============================================================================================
	public TileEditCanvas(EditorMain e)
	{//===============================================================================================
		E=e;


		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		addKeyListener(E);



	}
	//===============================================================================================
	public void paint()
	{//===============================================================================================
		paint(getGraphics());

	}


	//===============================================================================================
	@Override
	public void paint(Graphics G)
	{//===============================================================================================

		//super.paint(G);

		//G = getGraphics(); <---- this is what caused it to not draw properly. don't do this!!!

		//*** Draw Selected Tile ***
		if(Project.getSelectedPalette() != null)
		{
			G.setColor(Project.getSelectedPalette().getColor(0));
		}
		else
		{
			G.setColor(Color.BLACK);
		}
		G.fillRect(EditGrid_x, EditGrid_y, (EditZoom * 8), (EditZoom * 8));
		G.setColor(Color.WHITE);
		G.drawRect(EditGrid_x - 1, EditGrid_y - 1, (EditZoom * 8) + 1, (EditZoom * 8) + 1);
		if(Project.tileset != null)
		{
			for(int y = 0; y < 8; y++)
			{
				for(int x = 0; x < 8; x++)
				{
					G.setColor(Project.tileset.getColor(EditorMain.tileCanvas.tileSelected, x, y));
					G.fillRect(EditGrid_x + x * EditZoom, EditGrid_y + y * EditZoom, EditZoom, EditZoom);                                                                             //------------------------------------- change
				}
			}
		}



	}

	//===============================================================================================
	public void fill(int tile, int sx, int sy, int color, int prevcolor)
	{//===============================================================================================
		Project.tileset.setPixel(tile, sx, sy, color);
		if(sx > 0 && Project.tileset.getPixel(tile, sx - 1, sy) != color && Project.tileset.getPixel(tile, sx - 1, sy) == prevcolor)
		{
			fill(tile, sx - 1, sy, color, prevcolor);
		}
		if(sx < 7 && Project.tileset.getPixel(tile, sx + 1, sy) != color && Project.tileset.getPixel(tile, sx + 1, sy) == prevcolor)
		{
			fill(tile, sx + 1, sy, color, prevcolor);
		}
		if(sy > 0 && Project.tileset.getPixel(tile, sx, sy - 1) != color && Project.tileset.getPixel(tile, sx, sy - 1) == prevcolor)
		{
			fill(tile, sx, sy - 1, color, prevcolor);
		}
		if(sy < 7 && Project.tileset.getPixel(tile, sx, sy + 1) != color && Project.tileset.getPixel(tile, sx, sy + 1) == prevcolor)
		{
			fill(tile, sx, sy + 1, color, prevcolor);
		}
	}



	//===============================================================================================
	@Override
	public void itemStateChanged(ItemEvent e)
	{//===============================================================================================
	}
	//===============================================================================================
	@Override
	public void actionPerformed(ActionEvent e)
	{//===============================================================================================

	}


	//===============================================================================================
	public void selectColor(int cs)
	{//===============================================================================================

		EditorMain.controlPanel.paletteCanvas.selectColor(cs);
	}

	//===============================================================================================
	@Override
	public void mouseClicked(MouseEvent me)
	{//===============================================================================================
		int leftMask = InputEvent.BUTTON1_MASK;
		int rightMask = InputEvent.BUTTON3_MASK;

		int ctrlClickMask = InputEvent.BUTTON1_MASK + InputEvent.CTRL_MASK;



		if(me.getY() > EditGrid_y
			&& me.getY() < EditGrid_y +(EditZoom * 8)
			&& me.getX() > EditGrid_x
			&& me.getX() < EditGrid_x +(EditZoom * 8))
		{
			if(me.getModifiers() == leftMask) //&& E.tileCanvas.tileSelected!=0 //palettePanel.colorSelected != E.project.getSelectedPalette().num_Colors  &&
			{
				if(me.getClickCount() == 2)
				{
					//Recursive Flood Fill
					fill(EditorMain.tileCanvas.tileSelected, (me.getX() - EditGrid_x) / EditZoom, (me.getY() - EditGrid_y) / EditZoom, EditorMain.controlPanel.paletteCanvas.colorSelected, EditorMain.controlPanel.paletteCanvas.prevcolor);
					EditorMain.infoLabel.setTextNoConsole("Flood Filled Tile Edit Area");
				}
				EditorMain.controlPanel.paletteCanvas.prevcolor = Project.tileset.getPixel(EditorMain.tileCanvas.tileSelected, (me.getX() - EditGrid_x) / EditZoom, (me.getY() - EditGrid_y) / EditZoom);
				Project.tileset.setPixel(EditorMain.tileCanvas.tileSelected, (me.getX() - EditGrid_x) / EditZoom, (me.getY() - EditGrid_y) / EditZoom, EditorMain.controlPanel.paletteCanvas.colorSelected);

				EditorMain.tileCanvas.paint(EditorMain.tileCanvas.tileSelected);

				EditorMain.mapCanvas.repaintTileEverywhereOnMap(EditorMain.tileCanvas.tileSelected);

				paint();

			}
			else if((me.getModifiers() == rightMask || me.getModifiers() == ctrlClickMask))
			{
				selectColor(Project.tileset.getPixel(EditorMain.tileCanvas.tileSelected, (me.getX() - EditGrid_x) / EditZoom, (me.getY() - EditGrid_y) / EditZoom));

			}

		}
	}
	//===============================================================================================
	@Override
	public void mousePressed(MouseEvent me)
	{//===============================================================================================

	}
	//===============================================================================================
	@Override
	public void mouseReleased(MouseEvent me)
	{//===============================================================================================
		int leftMask = InputEvent.BUTTON1_MASK;


		if(me.getY() > EditGrid_y
			&& me.getY() < EditGrid_y + (EditZoom * 8) &&
			me.getX() > EditGrid_x
			&& me.getX() < EditGrid_x + (EditZoom * 8))
		{
			if(me.getModifiers() == leftMask)//colorSelected != E.project.getSelectedPalette().num_Colors &&
			{

				EditorMain.tileCanvas.paint(EditorMain.tileCanvas.tileSelected);
				EditorMain.tileCanvas.paint();

				EditorMain.mapCanvas.repaintTileEverywhereOnMap(EditorMain.tileCanvas.tileSelected);
				paint();
			}
		}

	}
	//===============================================================================================
	@Override
	public void mouseEntered(MouseEvent e)
	{//===============================================================================================

	}
	//===============================================================================================
	@Override
	public void mouseExited(MouseEvent e)
	{//===============================================================================================

	}
	//===============================================================================================
	@Override
	public void mouseDragged(MouseEvent me)
	{//===============================================================================================
		int leftMask = InputEvent.BUTTON1_MASK;

			if(me.getY() > EditGrid_y
			&& me.getY() < EditGrid_y + (EditZoom * 8)
			&& me.getX() > EditGrid_x
			&& me.getX() < EditGrid_x + (EditZoom * 8))
		{
			if(me.getModifiers() == leftMask)//colorSelected != E.project.getSelectedPalette().num_Colors /*&& E.tileCanvas.tileSelected!=0*/ &&
			{
				Project.tileset.setPixel(EditorMain.tileCanvas.tileSelected, (me.getX() - EditGrid_x) / EditZoom, (me.getY() - EditGrid_y) / EditZoom, EditorMain.controlPanel.paletteCanvas.colorSelected);

				EditorMain.tileCanvas.paint(EditorMain.tileCanvas.tileSelected);
				paint();

				//E.mapCanvas.repaintTileEverywhereOnMap(E.tileCanvas.tileSelected);
				//don't redraw the map until we let up on the mousebutton, otherwise it is slow, especially for large maps.
			}
		}

	}
	//===============================================================================================
	@Override
	public void mouseMoved(MouseEvent e)
	{//===============================================================================================

	}




}

