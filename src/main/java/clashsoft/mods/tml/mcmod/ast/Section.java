package clashsoft.mods.tml.mcmod.ast;

import java.util.List;

public class Section
{
	private final Section parent;
	private List<Section> subSections;
	
	public Section(Section parent)
	{
		this.parent = parent;
	}
	
	public Section getParent()
	{
		return this.parent;
	}
	
	public List<Section> getSubSections()
	{
		return this.subSections;
	}
	
	public void addSubSection(Section subSection)
	{
		if (this.subSections != null)
		{
			this.subSections.add(subSection);
		}
	}
}
